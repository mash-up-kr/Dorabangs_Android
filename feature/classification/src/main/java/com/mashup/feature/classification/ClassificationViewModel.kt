package com.mashup.feature.classification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.mashup.dorabangs.core.coroutine.doraLaunch
import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChipUiModel
import com.mashup.dorabangs.domain.model.classification.AIClassificationFeedPost
import com.mashup.dorabangs.domain.usecase.aiclassification.GetAIClassificationFolderListUseCase
import com.mashup.dorabangs.domain.usecase.aiclassification.GetAIClassificationPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ClassificationViewModel @Inject constructor(
    private val getAIClassificationFolderListUseCase: GetAIClassificationFolderListUseCase,
    private val getAIClassificationPostsUseCase: GetAIClassificationPostsUseCase,
) : ViewModel(),
    ContainerHost<ClassificationState, ClassificationSideEffect> {
    override val container =
        container<ClassificationState, ClassificationSideEffect>(ClassificationState())

    companion object {
        private const val LIMIT = 10
        private const val DESC = "desc"
    }

    init {
        getInitialData()
    }

    private fun getInitialData() = viewModelScope.doraLaunch {
        val chips = getAIClassificationFolderListUseCase.invoke()
        val paging = getAIClassificationPostsUseCase.invoke(
            limit = LIMIT,
            order = DESC,
        ).cachedIn(viewModelScope)
            .map { pagedData ->
                pagedData.map {
                    val category =
                        chips.list.firstOrNull { chip -> chip.folderId == it.folderId }?.folderName.orEmpty()
                    it.toUiModel(category)
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = PagingData.empty(),
            )
        intent {
            reduce {
                chips.list.map {
                    val postCount = if (it.postCount > 99) "99+" else it.postCount
                    DoraChipUiModel(
                        id = it.folderId,
                        title = "${it.folderName} $postCount",
                        icon = it.icon,
                        postCount = it.postCount,
                    )
                }.let { chipList ->
                    state.copy(
                        cardInfoList = paging,
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

    fun moveSelectedItem(idx: Int) = intent {
    }

    fun deleteSelectedItem(idx: Int) = intent {
    }
}

fun AIClassificationFeedPost.toUiModel(matchedCategory: String) = FeedCardUiModel(
    postId = postId,
    folderId = folderId,
    title = title,
    content = content,
    category = matchedCategory,
    createdAt = createdAt,
    keywordList = keywordList,
    thumbnail = thumbnail,
    isFavorite = false,
    isLoading = false,
)
