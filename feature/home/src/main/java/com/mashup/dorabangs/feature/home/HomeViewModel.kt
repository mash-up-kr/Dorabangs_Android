package com.mashup.dorabangs.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashup.dorabangs.core.coroutine.doraLaunch
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChipUiModel
import com.mashup.dorabangs.domain.model.NewFolderNameList
import com.mashup.dorabangs.domain.usecase.folder.CreateFolderUseCase
import com.mashup.dorabangs.domain.usecase.user.GetLastCopiedUrlUseCase
import com.mashup.dorabangs.domain.usecase.user.SetLastCopiedUrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val getLastCopiedUrlUseCase: GetLastCopiedUrlUseCase,
    private val setLastCopiedUrlUseCase: SetLastCopiedUrlUseCase,
    private val createFolderUseCase: CreateFolderUseCase,
) : ViewModel(), ContainerHost<HomeState, HomeSideEffect> {
    override val container = container<HomeState, HomeSideEffect>(HomeState())

    init {
        viewModelScope.doraLaunch {
            savedStateHandle.getStateFlow(
                "isVisibleMovingBottomSheet",
                initialValue = false,
            ).collect { isVisible -> setVisibleMovingFolderBottomSheet(visible = isVisible) }
        }
    }

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

    fun setLocalCopiedUrl(url: String) = viewModelScope.doraLaunch {
        setLastCopiedUrlUseCase.invoke(url = url)
    }

    suspend fun getLocalCopiedUrl() = getLastCopiedUrlUseCase.invoke().firstOrNull()

    fun setVisibleMoreButtonBottomSheet(visible: Boolean) = intent {
        reduce {
            state.copy(isShowMoreButtonSheet = visible)
        }
    }

    fun setVisibleDialog(visible: Boolean) = intent {
        reduce {
            state.copy(isShowDialog = visible)
        }
    }

    fun setVisibleMovingFolderBottomSheet(visible: Boolean, isNavigate: Boolean = false) = intent {
        reduce {
            state.copy(isShowMovingFolderSheet = visible)
        }
        if (isNavigate) postSideEffect(HomeSideEffect.NavigateToCreateFolder)
    }

    fun createFolder(folderName: String) {
        viewModelScope.doraLaunch {
            val folderData = NewFolderNameList(names = listOf(folderName))
            val isCreateFolderSuccess = createFolderUseCase(folderData)
            if (isCreateFolderSuccess.isSuccess) {
                intent {
                    postSideEffect(HomeSideEffect.NavigateToHome)
                }
            } else {
                setTextHelperEnable(
                    isEnable = true,
                    helperMsg = isCreateFolderSuccess.errorMsg,
                )
            }
        }
    }

    private fun setTextHelperEnable(isEnable: Boolean, helperMsg: String) = intent {
        reduce { state.copy(homeCreateFolder = state.homeCreateFolder.copy(helperEnable = isEnable, helperMessage = helperMsg)) }
    }

    fun setFolderName(folderName: String) = intent {
        reduce {
            state.copy(homeCreateFolder = state.homeCreateFolder.copy(folderName = folderName))
        }
    }

    init {
        intent {
            reduce {
                HomeState(
                    tapElements = listOf(
                        DoraChipUiModel(
                            title = "전체",
                            icon = R.drawable.ic_plus,
                        ),
                        DoraChipUiModel(
                            title = "즐겨찾기",
                            icon = R.drawable.ic_plus,
                        ),
                        DoraChipUiModel(
                            title = "나중에 읽을 링크",
                            icon = R.drawable.ic_plus,
                        ),
                        DoraChipUiModel(
                            title = "테스트",
                        ),
                        DoraChipUiModel(
                            title = "테스트",
                        ),
                        DoraChipUiModel(
                            title = "테스트",
                        ),
                    ),
                    feedCards = listOf(
                        FeedCardUiModel(
                            id = "",
                            title = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                            content = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                            keywordList = listOf("다연", "호현", "석주"),
                            category = "디자인",
                            createdAt = "2024-07-18T15:50:36.181Z",
                            thumbnail = "",
                            isLoading = true,
                        ),
                        FeedCardUiModel(
                            id = "",
                            title = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                            content = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                            keywordList = listOf("다연", "호현", "석주"),
                            category = "디자인",
                            createdAt = "2024-07-18T15:50:36.181Z",
                            thumbnail = "",
                        ),
                        FeedCardUiModel(
                            id = "",
                            title = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                            content = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                            keywordList = listOf("다연", "호현", "석주"),
                            category = "디자인",
                            createdAt = "2024-07-18T15:50:36.181Z",
                            thumbnail = "",
                        ),
                        FeedCardUiModel(
                            id = "",
                            title = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                            content = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                            keywordList = listOf("다연", "호현", "석주"),
                            category = "디자인",
                            createdAt = "2024-07-18T15:50:36.181Z",
                            thumbnail = "",
                        ),
                        FeedCardUiModel(
                            id = "",
                            title = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                            content = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                            keywordList = listOf("다연", "호현", "석주"),
                            category = "디자인",
                            createdAt = "2024-07-18T15:50:36.181Z",
                            thumbnail = "",
                        ),
                    ),
                )
            }
        }
    }
}
