package com.mashup.dorabangs.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChipUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel(), ContainerHost<HomeState, HomeSideEffect> {
    override val container = container<HomeState, HomeSideEffect>(HomeState())

    fun changeSelectedTapIdx(index: Int) = intent {
        reduce {
            state.copy(selectedIndex = index)
        }
    }

    fun hideSnackBar() = intent {
        postSideEffect(HomeSideEffect.HideSnackBar)
    }

    fun showSnackBar(clipboardText: String) = intent {
        state.copy(
            clipBoardState = state.clipBoardState.copy(
                copiedText = clipboardText,
            ),
        ).also { newState ->
            if (newState.clipBoardState.isValidUrl) {
                reduce { newState }
                postSideEffect(
                    HomeSideEffect.ShowSnackBar(
                        copiedText = newState.clipBoardState.copiedText,
                    ),
                )
            }
        }
    }

    init {
        intent {
            reduce {
                HomeState(
                    tapElements = listOf(
                        DoraChipUiModel(
                            title = "전체 99+",
                            icon = R.drawable.ic_plus,
                        ),
                        DoraChipUiModel(
                            title = "하이?",
                        ),
                        DoraChipUiModel(
                            title = "바이?",
                        ),
                        DoraChipUiModel(
                            title = "바이?",
                        ),
                        DoraChipUiModel(
                            title = "바이?",
                            icon = R.drawable.ic_plus,
                        ),
                        DoraChipUiModel(
                            title = "전체 99+",
                            icon = R.drawable.ic_plus,
                        ),
                        DoraChipUiModel(
                            title = "하이?",
                        ),
                        DoraChipUiModel(
                            title = "바이?",
                        ),
                        DoraChipUiModel(
                            title = "바이?",
                        ),
                        DoraChipUiModel(
                            title = "바이?",
                            icon = R.drawable.ic_plus,
                        ),
                    ),
                    feedCards = listOf(
                        FeedCardUiModel(
                            title = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                            content = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                            keywordList = listOf("다연", "호현", "석주"),
                            category = "디자인",
                            createdAt = 1,
                            thumbnail = androidx.core.R.drawable.ic_call_answer,
                            isLoading = true,
                        ),
                        FeedCardUiModel(
                            title = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                            content = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                            keywordList = listOf("다연", "호현", "석주"),
                            category = "디자인",
                            createdAt = 1,
                            thumbnail = androidx.core.R.drawable.ic_call_answer,
                        ),
                        FeedCardUiModel(
                            title = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                            content = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                            keywordList = listOf("다연", "호현", "석주"),
                            category = "디자인",
                            createdAt = 1,
                            thumbnail = androidx.core.R.drawable.ic_call_answer,
                        ),
                        FeedCardUiModel(
                            title = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                            content = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                            keywordList = listOf("다연", "호현", "석주"),
                            category = "디자인",
                            createdAt = 1,
                            thumbnail = androidx.core.R.drawable.ic_call_answer,
                        ),
                        FeedCardUiModel(
                            title = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                            content = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                            keywordList = listOf("다연", "호현", "석주"),
                            category = "디자인",
                            createdAt = 1,
                            thumbnail = androidx.core.R.drawable.ic_call_answer,
                        ),
                    ),
                )
            }
        }
    }
}
