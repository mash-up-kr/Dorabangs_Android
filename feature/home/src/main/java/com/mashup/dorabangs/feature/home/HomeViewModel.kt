package com.mashup.dorabangs.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.mashup.dorabangs.core.coroutine.doraLaunch
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChipUiModel
import com.mashup.dorabangs.domain.model.Link
import com.mashup.dorabangs.domain.model.NewFolderNameList
import com.mashup.dorabangs.domain.model.Sort
import com.mashup.dorabangs.domain.usecase.aiclassification.GetAIClassificationCountUseCase
import com.mashup.dorabangs.domain.usecase.folder.CreateFolderUseCase
import com.mashup.dorabangs.domain.usecase.folder.GetFolderListUseCase
import com.mashup.dorabangs.domain.usecase.posts.GetPosts
import com.mashup.dorabangs.domain.usecase.posts.GetUnReadPostsCountUseCase
import com.mashup.dorabangs.domain.usecase.posts.SaveLinkUseCase
import com.mashup.dorabangs.domain.usecase.user.GetLastCopiedUrlUseCase
import com.mashup.dorabangs.domain.usecase.user.SetLastCopiedUrlUseCase
import com.mashup.dorabangs.feature.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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
    private val getFolderList: GetFolderListUseCase,
    private val getPostsUseCase: GetPosts,
    private val setLastCopiedUrlUseCase: SetLastCopiedUrlUseCase,
    private val createFolderUseCase: CreateFolderUseCase,
    private val saveLinkUseCase: SaveLinkUseCase,
    private val getUnReadPostsCountUseCase: GetUnReadPostsCountUseCase,
    private val getAIClassificationCount: GetAIClassificationCountUseCase,
) : ViewModel(), ContainerHost<HomeState, HomeSideEffect> {
    override val container = container<HomeState, HomeSideEffect>(HomeState())

    init {
        viewModelScope.doraLaunch {
            launch {
                savedStateHandle.getStateFlow(
                    "isVisibleMovingBottomSheet",
                    initialValue = false,
                ).collect { isVisible -> setVisibleMovingFolderBottomSheet(visible = isVisible) }
            }
            launch {
                savedStateHandle.getStateFlow(
                    "urlLink",
                    initialValue = "",
                ).collect { urlLink -> setStateUrlLink(urlLink) }
            }
        }

        updateFolderList()
        setAIClassificationCount()
        setPostsCount()
        getSavedLinkFromDefaultFolder()
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

    private fun setStateUrlLink(urlLink: String) = intent {
        reduce {
            state.copy(homeCreateFolder = state.homeCreateFolder.copy(urlLink = urlLink))
        }
    }

    private fun updateFolderList() = viewModelScope.doraLaunch {
        val folderList = getFolderList()
        intent {
            reduce {
                state.copy(
                    tapElements = folderList.toList().mapIndexed { index, folder ->
                        DoraChipUiModel(
                            id = folder.id.orEmpty(),
                            title = folder.name,
                            icon = setDefaultFolderIcon(index),
                        )
                    },
                )
            }
        }
    }

    private fun setDefaultFolderIcon(index: Int) = when (index) {
        0 -> R.drawable.ic_3d_all_small
        1 -> R.drawable.ic_3d_bookmark_small
        2 -> R.drawable.ic_3d_pin_small
        else -> null
    }

    private fun setTextHelperEnable(isEnable: Boolean, helperMsg: String) = intent {
        reduce {
            state.copy(
                homeCreateFolder = state.homeCreateFolder.copy(
                    helperEnable = isEnable,
                    helperMessage = helperMsg,
                ),
            )
        }
    }

    fun createFolder(folderName: String, urlLink: String) {
        viewModelScope.doraLaunch {
            val folderData = NewFolderNameList(names = listOf(folderName))
            val afterCreateFolder = createFolderUseCase(folderData)
            if (afterCreateFolder.isSuccess) {
                // TODO API 바뀌면 id 넣는 로직 추가
                intent {
                    if (urlLink.isNotBlank()) {
                        postSideEffect(
                            HomeSideEffect.SaveLink(
                                folderId = "afterCreateFolder.folderId",
                                urlLink = urlLink,
                            ),
                        )
                    } else {
                        postSideEffect(HomeSideEffect.NavigateToHome)
                    }
                }
            } else {
                setTextHelperEnable(
                    isEnable = true,
                    helperMsg = afterCreateFolder.errorMsg,
                )
            }
        }
    }

    fun saveLink(folderId: String, urlLink: String) = viewModelScope.doraLaunch {
        saveLinkUseCase.invoke(Link(folderId = folderId, url = urlLink))

        intent {
            postSideEffect(
                HomeSideEffect.NavigateHomeAfterSaveLink,
            )
        }
    }

    fun setFolderName(folderName: String) = intent {
        reduce {
            state.copy(homeCreateFolder = state.homeCreateFolder.copy(folderName = folderName))
        }
    }

    private fun setAIClassificationCount() = viewModelScope.launch {
        val count = getAIClassificationCount()
        intent {
            reduce {
                state.copy(aiClassificationCount = count)
            }
        }
    }

    private fun setPostsCount() = viewModelScope.launch {
        val count = getUnReadPostsCountUseCase()
        intent {
            reduce {
                state.copy(unReadPostCount = count)
            }
        }
    }

    private fun getSavedLinkFromDefaultFolder(
        order: String = Sort.ASC.name,
        favorite: Boolean = false,
        isRead: Boolean? = null,
    ) = viewModelScope.doraLaunch {
        val pagingData =
            getPostsUseCase.invoke(order = order, favorite = favorite, isRead = isRead)
                .cachedIn(viewModelScope).map { pagedData ->
                    pagedData.map { savedLinkInfo -> savedLinkInfo.toUiModel() }
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.Lazily,
                    initialValue = PagingData.empty(),
                )
        intent {
            reduce {
                state.copy(feedCards = pagingData)
            }
        }
    }
}
