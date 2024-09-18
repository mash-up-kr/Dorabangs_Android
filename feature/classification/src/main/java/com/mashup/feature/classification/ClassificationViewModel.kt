package com.mashup.feature.classification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.insertSeparators
import androidx.paging.map
import com.mashup.dorabangs.core.coroutine.doraLaunch
import com.mashup.dorabangs.core.designsystem.component.chips.FeedUiModel
import com.mashup.dorabangs.domain.model.AIClassificationFolders
import com.mashup.dorabangs.domain.model.PostInfo
import com.mashup.dorabangs.domain.model.Sort
import com.mashup.dorabangs.domain.model.classification.AIClassificationFeedPost
import com.mashup.dorabangs.domain.usecase.aiclassification.DeletePostFromAIClassificationUseCase
import com.mashup.dorabangs.domain.usecase.aiclassification.GetAIClassificationFolderListUseCase
import com.mashup.dorabangs.domain.usecase.aiclassification.GetAIClassificationPostsByFolderUseCase
import com.mashup.dorabangs.domain.usecase.aiclassification.GetAIClassificationPostsUseCase
import com.mashup.dorabangs.domain.usecase.aiclassification.MoveAllPostsToRecommendedFolderUseCase
import com.mashup.dorabangs.domain.usecase.aiclassification.MoveSinglePostToRecommendedFolderUseCase
import com.mashup.dorabangs.domain.usecase.posts.PatchPostInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ClassificationViewModel @Inject constructor(
    private val getAIClassificationFolderListUseCase: GetAIClassificationFolderListUseCase,
    private val getAIClassificationPostsUseCase: GetAIClassificationPostsUseCase,
    private val getAIClassificationFolderListByIdUseCase: GetAIClassificationPostsByFolderUseCase,
    private val deletePostUseCase: DeletePostFromAIClassificationUseCase,
    private val moveSinglePostUseCase: MoveSinglePostToRecommendedFolderUseCase,
    private val moveAllPostUseCase: MoveAllPostsToRecommendedFolderUseCase,
    private val patchPostInfoUseCase: PatchPostInfoUseCase,
) : ViewModel(),
    ContainerHost<ClassificationState, ClassificationSideEffect> {
    override val container =
        container<ClassificationState, ClassificationSideEffect>(ClassificationState())
    private val _paging = MutableStateFlow<PagingData<FeedUiModel>>(PagingData.empty())
    val paging = _paging.asStateFlow()

    companion object {
        private const val LIMIT = 10
        private const val DESC = "desc"
    }

    init {
        getInitialData()
    }

    private fun getInitialData() = viewModelScope.doraLaunch {
        val chips = getAIClassificationFolderListUseCase.invoke()
        intent {
            reduce {
                doraChipMapper(chips).let { chipList ->
                    state.copy(
                        isLoading = true,
                        chipState = ChipState(
                            totalCount = chips.totalCounts,
                            chipList = chipList,
                        ),
                    )
                }
            }
            getAIClassificationPostsUseCase.invoke(
                limit = LIMIT,
                order = DESC,
            ).map { pagedData ->
                pagedData.map {
                    val category =
                        chips.list.firstOrNull { chip -> chip.folderId == it.folderId }?.folderName.orEmpty()
                    it.toUiModel(category)
                }
            }.map {
                it.insertSeparators { before, after ->
                    after?.let {
                        if (before?.category != after.category) {
                            FeedUiModel.DoraChipUiModel(
                                id = after.postId,
                                mergedTitle = "",
                                title = after.category.orEmpty(),
                                postCount = chips.list
                                    .firstOrNull { chip -> chip.folderName == after.category.orEmpty() }
                                    ?.postCount ?: 0,
                                folderId = after.folderId,
                                icon = null,
                            )
                        } else {
                            null
                        }
                    }
                }
            }
                .cachedIn(viewModelScope)
                .let {
                    _paging.value = it.firstOrNull() ?: PagingData.empty()
                }
        }
    }.invokeOnCompletion {
        intent {
            reduce {
                state.copy(isLoading = false)
            }
        }
    }

    fun changeCategory(idx: Int) = viewModelScope.doraLaunch {
        intent {
            val selectedFolderItem = state.chipState.chipList.getOrNull(idx)
            val selectedFolderId = selectedFolderItem?.id.orEmpty()
            val selectedFolderName = selectedFolderItem?.title.orEmpty()

            getAIClassificationFolderListByIdUseCase.invoke(
                folderId = selectedFolderId,
                limit = LIMIT,
                order = Sort.DESC,
            ).map { pagedData ->
                // chip create
                pagedData.map {
                    val category =
                        state.chipState.chipList.firstOrNull { chip -> chip.id == it.folderId }?.title.orEmpty()
                    it.toUiModel(category)
                }
            }.map {
                // add seperator
                it.insertSeparators { before, after ->
                    after?.let {
                        if (before?.category != after.category) {
                            FeedUiModel.DoraChipUiModel(
                                id = selectedFolderId,
                                mergedTitle = "",
                                title = selectedFolderName,
                                postCount = selectedFolderItem?.postCount ?: 0,
                                folderId = after.folderId,
                                icon = null,
                            )
                        } else {
                            null
                        }
                    }
                }
            }.cachedIn(viewModelScope)
                .let {
                    _paging.value = it.firstOrNull() ?: PagingData.empty()
                }

            reduce {
                state.copy(
                    chipState = state.chipState.copy(currentIndex = idx),
                    selectedFolder = state.chipState.chipList.getOrNull(idx)?.title ?: "전체",
                )
            }
        }
    }

    fun moveAllItems(folderId: String) = viewModelScope.doraLaunch {
        intent {
            reduce {
                state.copy(isLoading = true)
            }
        }
        val allMove = moveAllPostUseCase.invoke(suggestionFolderId = folderId)

        if (allMove.isSuccess) {
            getInitialData()
        }
    }.invokeOnCompletion {
        intent {
            reduce { state.copy(isLoading = false) }
        }
    }

    fun moveSelectedItem(cardItem: FeedUiModel.FeedCardUiModel) = viewModelScope.doraLaunch {
        intent {
            reduce {
                state.copy(
                    isLoading = true,
                )
            }
            val move = moveSinglePostUseCase.invoke(
                postId = cardItem.postId,
                suggestionFolderId = cardItem.folderId,
            )

            if (move.isSuccess) {
                val (chips, chipList) = updateChipList()
                val findCategory = chips.list.find { it.folderId == cardItem.folderId }

                if (findCategory != null) {
                    updateListScreenWithSingleItem(cardItem)
                    updateSeparator(chipList)
                } else {
                    getInitialData()
                }

                reduce {
                    state.copy(
                        chipState = state.chipState.copy(
                            totalCount = chips.totalCounts,
                            chipList = chipList,
                        ),
                    )
                }
            }
        }
    }.invokeOnCompletion {
        intent {
            reduce {
                state.copy(
                    isLoading = false
                )
            }
        }
    }

    fun deleteSelectedItem(cardItem: FeedUiModel.FeedCardUiModel) = viewModelScope.doraLaunch {
        intent {
            reduce {
                state.copy(
                    isLoading = true,
                )
            }
            val delete = deletePostUseCase.invoke(cardItem.postId)
            if (delete.isSuccess) {
                val (chips, chipList) = updateChipList()
                val findCategory = chips.list.find { it.folderId == cardItem.folderId }

                if (findCategory != null) {
                    updateListScreenWithSingleItem(cardItem)
                    updateSeparator(chipList)
                } else {
                    getInitialData()
                }

                reduce {
                    state.copy(
                        chipState = state.chipState.copy(
                            totalCount = chips.totalCounts,
                            chipList = chipList,
                        ),
                    )
                }
            }
        }
    }.invokeOnCompletion {
        intent {
            reduce {
                state.copy(
                    isLoading = false,
                )
            }
        }
    }

    private fun updateSeparator(chipList: List<FeedUiModel.DoraChipUiModel>) {
        _paging.value = _paging.value.map {
            if (it is FeedUiModel.DoraChipUiModel) {
                val updateItem = chipList.find { chip -> chip.id == it.folderId }
                it.copy(postCount = updateItem?.postCount ?: it.postCount)
            } else it
        }
    }

    private suspend fun updateListScreenWithSingleItem(cardItem: FeedUiModel.FeedCardUiModel) {
        // list update
        paging.map { pagingData ->
            pagingData.filter {
                when (it) {
                    is FeedUiModel.DoraChipUiModel -> true
                    is FeedUiModel.FeedCardUiModel -> {
                        it.postId != cardItem.postId
                    }
                }
            }
        }.let {
            _paging.value = it.firstOrNull() ?: PagingData.empty()
        }
    }

    private suspend fun updateChipList(): Pair<AIClassificationFolders, List<FeedUiModel.DoraChipUiModel>> {
        val chips = getAIClassificationFolderListUseCase.invoke()
        val chipList = doraChipMapper(chips)
        return Pair(chips, chipList)
    }

    private fun doraChipMapper(chips: AIClassificationFolders): List<FeedUiModel.DoraChipUiModel> {
        val firstChip = listOf(
            FeedUiModel.DoraChipUiModel(
                id = "",
                mergedTitle = "전체 ${chips.totalCounts}",
                title = "전체",
                postCount = chips.totalCounts,
            ),
        )
        return firstChip + chips.list.map {
            val postCount = if (it.postCount > 99) "99+" else it.postCount
            FeedUiModel.DoraChipUiModel(
                id = it.folderId,
                mergedTitle = "${it.folderName} $postCount",
                title = it.folderName,
                icon = it.icon,
                postCount = it.postCount,
            )
        }
    }

    /**
     * 웹뷰 이동 시 읽음 처리
     */
    fun updateReadAt(cardInfo: FeedUiModel.FeedCardUiModel) = viewModelScope.doraLaunch {
        intent {
            if (cardInfo.readAt.isNullOrEmpty()) {
                patchPostInfoUseCase.invoke(
                    postId = cardInfo.postId,
                    PostInfo(readAt = FeedUiModel.FeedCardUiModel.createCurrentTime()),
                )
            }
        }
    }
}

fun AIClassificationFeedPost.toUiModel(matchedCategory: String) = FeedUiModel.FeedCardUiModel(
    postId = postId,
    folderId = folderId,
    title = title,
    content = content,
    category = matchedCategory,
    createdAt = createdAt,
    keywordList = keywordList.take(3),
    thumbnail = thumbnail,
    isFavorite = false,
    url = url,
    isLoading = false,
    readAt = readAt,
)
