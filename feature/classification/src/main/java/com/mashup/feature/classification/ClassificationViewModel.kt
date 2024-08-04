package com.mashup.feature.classification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.insertSeparators
import androidx.paging.map
import com.mashup.dorabangs.core.coroutine.doraLaunch
import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChipUiModel
import com.mashup.dorabangs.domain.model.AIClassificationFolders
import com.mashup.dorabangs.domain.model.classification.AIClassificationFeedPost
import com.mashup.dorabangs.domain.usecase.aiclassification.DeletePostFromAIClassificationUseCase
import com.mashup.dorabangs.domain.usecase.aiclassification.GetAIClassificationFolderListUseCase
import com.mashup.dorabangs.domain.usecase.aiclassification.GetAIClassificationPostsUseCase
import com.mashup.dorabangs.domain.usecase.aiclassification.MoveSinglePostToRecommendedFolderUseCase
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
    private val deletePostUseCase: DeletePostFromAIClassificationUseCase,
    private val moveSinglePostUseCase: MoveSinglePostToRecommendedFolderUseCase,
) : ViewModel(),
    ContainerHost<ClassificationState, ClassificationSideEffect> {
    override val container =
        container<ClassificationState, ClassificationSideEffect>(ClassificationState())
    private val _paging = MutableStateFlow<PagingData<FeedCardUiModel>>(PagingData.empty())
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
        getAIClassificationPostsUseCase.invoke(
            limit = LIMIT,
            order = DESC,
        ).map { pagedData ->
            pagedData.map {
                val category =
                    chips.list.firstOrNull { chip -> chip.folderId == it.folderId }?.folderName.orEmpty()
                it.toUiModel(category)
            }
        }
            .cachedIn(viewModelScope)
            .let {
                _paging.value = it.firstOrNull() ?: PagingData.empty()
            }

        intent {
            reduce {
                doraChipMapper(chips).let { chipList ->
                    state.copy(
                        chipState = ChipState(
                            totalCount = chips.totalCounts,
                            chipList = chipList,
                        ),
                    )
                }
            }
        }
    }

    fun changeCategory(idx: Int) = intent {
        reduce {
            state.copy(
                chipState = state.chipState.copy(currentIndex = idx),
            )
        }
    }

    fun moveAllItems() = intent {
    }

    fun moveSelectedItem(cardItem: FeedCardUiModel) = viewModelScope.doraLaunch {
        val move = moveSinglePostUseCase.invoke(
            postId = cardItem.postId,
            suggestionFolderId = cardItem.folderId,
        )

        if (move.isSuccess) {
            val (chips, chipList) = updateListScreen(cardItem)
            intent {
                reduce {
                    state.copy(
                        chipState = ChipState(
                            totalCount = chips.totalCounts,
                            chipList = chipList,
                        ),
                    )
                }
            }
        }
    }

    fun deleteSelectedItem(cardItem: FeedCardUiModel) = viewModelScope.doraLaunch {
        val delete = deletePostUseCase.invoke(cardItem.postId)
        if (delete.isSuccess) {
            val (chips, chipList) = updateListScreen(cardItem)
            intent {
                reduce {
                    state.copy(
                        chipState = ChipState(
                            totalCount = chips.totalCounts,
                            chipList = chipList,
                        ),
                    )
                }
            }
        }
    }

    private suspend fun updateListScreen(cardItem: FeedCardUiModel): Pair<AIClassificationFolders, List<DoraChipUiModel>> {
        val chips = getAIClassificationFolderListUseCase.invoke()
        val chipList = doraChipMapper(chips)

        paging.map { pagingData ->
            pagingData.filter { it.postId != cardItem.postId }
        }.let {
            _paging.value = it.firstOrNull() ?: PagingData.empty()
        }
        return Pair(chips, chipList)
    }

    private fun doraChipMapper(chips: AIClassificationFolders) =
        chips.list.map {
            val postCount = if (it.postCount > 99) "99+" else it.postCount
            DoraChipUiModel(
                id = it.folderId,
                mergedTitle = "${it.folderName} $postCount",
                title = it.folderName,
                icon = it.icon,
                postCount = it.postCount,
            )
        }
}

fun AIClassificationFeedPost.toUiModel(matchedCategory: String) = FeedCardUiModel(
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
)
