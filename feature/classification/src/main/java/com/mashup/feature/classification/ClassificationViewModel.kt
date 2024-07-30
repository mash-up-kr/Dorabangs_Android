package com.mashup.feature.classification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashup.dorabangs.core.coroutine.doraLaunch
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChipUiModel
import com.mashup.dorabangs.domain.usecase.aiclassification.GetAIClassificationFolderListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ClassificationViewModel @Inject constructor(
    private val getAIClassificationFolderListUseCase: GetAIClassificationFolderListUseCase,
) : ViewModel(),
    ContainerHost<ClassificationState, ClassificationSideEffect> {
    override val container =
        container<ClassificationState, ClassificationSideEffect>(ClassificationState())

    init {
        getInitialChipData()
    }

    private fun getInitialChipData() = viewModelScope.doraLaunch {
        val chips = getAIClassificationFolderListUseCase.invoke()
        intent {
            reduce {
                chips.list.map {
                    val postCount = if (it.postCount > 99) "99+" else it.postCount
                    DoraChipUiModel(
                        id = it.folderId,
                        title = "${it.folderName} $postCount",
                        icon = null,
                    )
                }.let { chipList ->
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

    fun moveSelectedItem(idx: Int) = intent {
    }

    fun deleteSelectedItem(idx: Int) = intent {
    }
}
