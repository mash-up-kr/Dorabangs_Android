package com.mashup.dorabangs.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.mashup.dorabangs.core.coroutine.doraLaunch
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.component.chips.FeedUiModel
import com.mashup.dorabangs.core.designsystem.component.toast.ToastStyle
import com.mashup.dorabangs.domain.model.FolderList
import com.mashup.dorabangs.domain.model.FolderType
import com.mashup.dorabangs.domain.model.Link
import com.mashup.dorabangs.domain.model.NewFolderNameList
import com.mashup.dorabangs.domain.model.PostInfo
import com.mashup.dorabangs.domain.model.Sort
import com.mashup.dorabangs.domain.usecase.aiclassification.GetAIClassificationCountUseCase
import com.mashup.dorabangs.domain.usecase.folder.CreateFolderUseCase
import com.mashup.dorabangs.domain.usecase.folder.GetFolderListUseCase
import com.mashup.dorabangs.domain.usecase.folder.GetSavedLinksFromFolderUseCase
import com.mashup.dorabangs.domain.usecase.posts.ChangePostFolder
import com.mashup.dorabangs.domain.usecase.posts.DeletePostUseCase
import com.mashup.dorabangs.domain.usecase.posts.GetPosts
import com.mashup.dorabangs.domain.usecase.posts.GetUnReadPostsCountUseCase
import com.mashup.dorabangs.domain.usecase.posts.PatchPostInfoUseCase
import com.mashup.dorabangs.domain.usecase.posts.SaveLinkUseCase
import com.mashup.dorabangs.domain.usecase.user.GetIdFromLinkToReadLaterUseCase
import com.mashup.dorabangs.domain.usecase.user.GetLastCopiedUrlUseCase
import com.mashup.dorabangs.domain.usecase.user.SetIdLinkToReadLaterUseCase
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
    private val getSavedLinksFromFolderUseCase: GetSavedLinksFromFolderUseCase,
    private val getUnReadPostsCountUseCase: GetUnReadPostsCountUseCase,
    private val getAIClassificationCount: GetAIClassificationCountUseCase,
    private val getIdFromLinkToReadLaterUseCase: GetIdFromLinkToReadLaterUseCase,
    private val setIdFromLinkToReadLaterUseCase: SetIdLinkToReadLaterUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val changePostFolderUseCase: ChangePostFolder,
    private val patchPostInfoUseCase: PatchPostInfoUseCase,
) : ViewModel(), ContainerHost<HomeState, HomeSideEffect> {
    override val container = container<HomeState, HomeSideEffect>(HomeState())

    init {
        viewModelScope.doraLaunch {
            launch {
                savedStateHandle.getStateFlow(
                    "isVisibleMovingBottomSheet",
                    initialValue = false,
                ).collect { isVisible -> if (isVisible) getCustomFolderList() }
            }
            launch {
                savedStateHandle.getStateFlow(
                    "urlLink",
                    initialValue = "",
                ).collect { urlLink -> setStateUrlLink(urlLink) }
            }
            launch {
                savedStateHandle.getStateFlow(
                    "isShowToast",
                    initialValue = false,
                ).collect { isShowToast ->
                    if (isShowToast) {
                        intent {
                            updateToastState("${state.changeFolderName}(으)로 이동했어요.")
                        }
                    }
                }
            }
        }

        updateFolderList()
        setAIClassificationCount()
        setPostsCount()
        getSavedLinkFromDefaultFolder(order = Sort.DESC.name)
    }

    fun changeSelectedTapIdx(index: Int) = intent {
        getSavedLinkFromDefaultFolder(
            folderId = state.tapElements[index].id,
            favorite = index == FAVORITE_FOLDER_INDEX,
        )

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
        val folderList = getFolderList().toList()
        if (getIdFromLinkToReadLaterUseCase.invoke().isBlank()) {
            folderList
                .firstOrNull { it.folderType == FolderType.DEFAULT }
                ?.let { folder ->
                    setIdFromLinkToReadLaterUseCase.invoke(
                        id = folder.id ?: error("서버가 또 잘못함 : 나중에 읽을 링크의 id가 null"),
                    )
                }
        }
        intent {
            reduce {
                state.copy(
                    tapElements = folderList.mapIndexed { index, folder ->
                        FeedUiModel.DoraChipUiModel(
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

    private fun setTextHelperEnable(
        isEnable: Boolean,
        helperMsg: String,
        lastCheckedFolderName: String,
    ) = intent {
        reduce {
            state.copy(
                homeCreateFolder = state.homeCreateFolder.copy(
                    helperEnable = isEnable,
                    helperMessage = helperMsg,
                    lastCheckedFolderName = lastCheckedFolderName,
                ),
            )
        }
    }

    fun createFolder(folderName: String, urlLink: String) {
        viewModelScope.doraLaunch {
            val folderData = NewFolderNameList(names = listOf(folderName))
            val afterCreateFolder = createFolderUseCase(folderData)
            if (afterCreateFolder.isSuccess) {
                intent {
                    if (urlLink.isNotBlank()) {
                        postSideEffect(
                            HomeSideEffect.SaveLink(
                                folderId = afterCreateFolder.result.firstOrNull()?.id
                                    ?: error("정확한 id가 안내려 왔어요"),
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
                    lastCheckedFolderName = folderName,
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
            state.copy(
                homeCreateFolder = state.homeCreateFolder.copy(
                    folderName = folderName,
                    helperEnable = folderName == state.homeCreateFolder.lastCheckedFolderName,
                ),
            )
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
        folderId: String? = null,
        favorite: Boolean = false,
        isRead: Boolean? = null,
    ) = viewModelScope.doraLaunch {
        val pagingData =
            if (folderId.isNullOrBlank()) {
                getPostsUseCase.invoke(order = order, favorite = favorite, isRead = isRead)
                    .map { pagedData ->
                        pagedData.map { savedLinkInfo -> savedLinkInfo.toUiModel() }
                    }.cachedIn(viewModelScope)
                    .stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.Lazily,
                        initialValue = PagingData.empty(),
                    )
            } else {
                getSavedLinksFromFolderUseCase.invoke(
                    folderId = folderId,
                    order = order,
                    limit = 10,
                    isRead = isRead,
                ).map { pagedData ->
                    pagedData.map { savedLinkInfo -> savedLinkInfo.toUiModel() }
                }.cachedIn(viewModelScope)
                    .stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.Lazily,
                        initialValue = PagingData.empty(),
                    )
            }
        intent {
            reduce {
                state.copy(feedCards = pagingData)
            }
        }
    }

    fun updateFavoriteItem(postId: String, isFavorite: Boolean) = viewModelScope.doraLaunch {
        intent {
            val postInfo = PostInfo(isFavorite = isFavorite)
            val response = patchPostInfoUseCase(
                postId = postId,
                postInfo = postInfo,
            )
            if (response.isSuccess) {
                postSideEffect(HomeSideEffect.RefreshPostList)
            }
        }
    }

    /**
     * 현재 폴더 리스트 가져오기
     */
    fun getCustomFolderList() = viewModelScope.doraLaunch {
        val folderList = getFolderList()
        intent {
            reduce {
                state.copy(folderList = filterDefaultList(folderList) + folderList.customFolders)
            }
            setVisibleMovingFolderBottomSheet(true)
        }
    }

    private fun filterDefaultList(list: FolderList) =
        listOf(
            list.defaultFolders.firstOrNull { it.folderType == FolderType.DEFAULT }
                ?: error("여기는 서버 잘못임 우리 탓 아님 ㄹㅇ"),
        )

    /**
     * 링크 삭제
     */
    fun deletePost(postId: String) = viewModelScope.doraLaunch {
        val response = deletePostUseCase(postId)
        setVisibleDialog(false)
        if (response.isSuccess) {
            intent {
                postSideEffect(HomeSideEffect.RefreshPostList)
                updateToastState("삭제 완료했어요.")
            }
        }
    }

    /**
     * 변환하고 싶은 링크의 postId와 folderId 임시 저장
     */
    fun updateSelectedPostItem(postId: String, folderId: String) = intent {
        reduce { state.copy(selectedPostId = postId, selectedFolderId = folderId) }
    }

    fun updateSelectFolderId(folderId: String, folderName: String = "") = intent {
        reduce { state.copy(changeFolderId = folderId, changeFolderName = folderName) }
    }

    /**
     * 링크 폴더 이동
     */
    fun moveFolder(postId: String, folderId: String, folderName: String) = viewModelScope.doraLaunch {
        val isSuccess = changePostFolderUseCase(postId = postId, folderId = folderId).isSuccess

        if (isSuccess) {
            intent {
                setVisibleMovingFolderBottomSheet(false)
                updateSelectFolderId(state.selectedFolderId)
                updateToastState("$folderName(으)로 이동했어요.")
            }
        }
    }

    /**
     * 링크 수정 - 새 폴더 추가 후 폴더 이동
     */
    fun createFolderWithMoveLink(folderName: String, postId: String) = viewModelScope.doraLaunch {
        val newFolder =
            createFolderUseCase.invoke(folderList = NewFolderNameList(names = listOf(folderName)))
        if (newFolder.isSuccess) {
            val newFolderId = newFolder.result.firstOrNull()?.id
            newFolderId?.let { folderId ->
                val moveFolder =
                    changePostFolderUseCase.invoke(postId = postId, folderId = folderId)
                if (moveFolder.isSuccess) {
                    intent {
                        reduce { state.copy(changeFolderName = folderName) }
                        postSideEffect(HomeSideEffect.NavigateToCompleteMovingFolder)
                        updateEditType(isEditPostFolder = false)
                    }
                } else {
                    setTextHelperEnable(
                        isEnable = true,
                        helperMsg = newFolder.errorMsg,
                        lastCheckedFolderName = folderName,
                    )
                }
            }
        }
    }

    fun updateEditType(isEditPostFolder: Boolean) = intent {
        reduce { state.copy(isEditPostFolder = isEditPostFolder) }
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

    private fun updateToastState(toastMsg: String) = intent {
        reduce {
            state.copy(
                toastState = state.toastState.copy(
                    text = toastMsg,
                    toastStyle = ToastStyle.CHECK,
                ),
            )
        }
        postSideEffect(HomeSideEffect.ShowToastSnackBar(toastMsg))
    }

    companion object {
        const val FAVORITE_FOLDER_INDEX = 1
    }
}
