package com.mashup.dorabangs.feature.storage.storagedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.mashup.dorabangs.core.coroutine.doraLaunch
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel
import com.mashup.dorabangs.core.designsystem.component.toast.ToastStyle
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.domain.model.FolderList
import com.mashup.dorabangs.domain.model.FolderType
import com.mashup.dorabangs.domain.model.PostInfo
import com.mashup.dorabangs.domain.usecase.folder.DeleteFolderUseCase
import com.mashup.dorabangs.domain.usecase.folder.GetFolderById
import com.mashup.dorabangs.domain.usecase.folder.GetFolderListUseCase
import com.mashup.dorabangs.domain.usecase.folder.GetSavedLinksFromFolderUseCase
import com.mashup.dorabangs.domain.usecase.posts.ChangePostFolder
import com.mashup.dorabangs.domain.usecase.posts.DeletePostUseCase
import com.mashup.dorabangs.domain.usecase.posts.GetPosts
import com.mashup.dorabangs.domain.usecase.posts.PatchPostInfoUseCase
import com.mashup.dorabangs.feature.storage.storagedetail.model.EditActionType
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailSideEffect
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailSort
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailState
import com.mashup.dorabangs.feature.storage.storagedetail.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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
class StorageDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val savedLinksFromFolderUseCase: GetSavedLinksFromFolderUseCase,
    private val patchPostInfoUseCase: PatchPostInfoUseCase,
    private val getPostsUseCase: GetPosts,
    private val deleteFolderUseCase: DeleteFolderUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val getFolderListUseCase: GetFolderListUseCase,
    private val changePostFolderUseCase: ChangePostFolder,
    private val getFolderByIdUseCase: GetFolderById,
) : ViewModel(), ContainerHost<StorageDetailState, StorageDetailSideEffect> {
    override val container = container<StorageDetailState, StorageDetailSideEffect>(StorageDetailState())

    private val _feedListState: MutableStateFlow<PagingData<FeedCardUiModel>> = MutableStateFlow(value = PagingData.empty())
    val feedListState: StateFlow<PagingData<FeedCardUiModel>> = _feedListState.asStateFlow()

    fun setFolderInfo(folderItem: Folder) = intent {
        reduce {
            state.copy(
                folderInfo = state.folderInfo.copy(
                    folderId = folderItem.id,
                    title = folderItem.name,
                    folderType = folderItem.folderType,
                ),
            )
        }
        fetchSavedLinkFromType(
            type = folderItem.folderType,
            folderId = folderItem.id,
        )
    }

    /**
     * 현재 폴더 정보 가져오기
     */
    fun getFolderInfoById(folderId: String, toastMsg: String) = viewModelScope.doraLaunch {
        val folderInfo = getFolderByIdUseCase(folderId = folderId)
        intent {
            reduce {
                state.copy(
                    folderInfo = state.folderInfo.copy(
                        folderId = folderInfo.id,
                        title = folderInfo.name,
                        postCount = folderInfo.postCount,
                        folderType = folderInfo.folderType,
                    ),
                    toastState = state.toastState.copy(
                        text = toastMsg,
                        toastStyle = ToastStyle.CHECK,
                    ),
                )
            }
            postSideEffect(StorageDetailSideEffect.ShowToastSnackBarRenameFolder)
        }
    }

    /**
     * 폴더 타입별 API 호출 분기 처리
     */
    private fun fetchSavedLinkFromType(
        type: FolderType = FolderType.ALL,
        folderId: String? = "",
        order: String = StorageDetailSort.ASC.name,
        isRead: Boolean? = null,
    ) {
        when (type) {
            FolderType.ALL -> getSavedLinkFromDefaultFolder(order = order, favorite = false, isRead = isRead)
            FolderType.FAVORITE -> getSavedLinkFromDefaultFolder(order = order, favorite = true, isRead = isRead)
            else -> getSavedLinkFromCustomFolder(folderId = folderId, order = order, isRead = isRead)
        }
    }

    /**
     * 사용자 지정 folder links
     */
    private fun getSavedLinkFromCustomFolder(
        folderId: String?,
        order: String = StorageDetailSort.ASC.name,
        limit: Int = 10,
        isRead: Boolean? = null,
    ) = viewModelScope.doraLaunch {
        savedLinksFromFolderUseCase.invoke(
            folderId = folderId,
            order = order,
            isRead = isRead,
            limit = limit,
            totalCount = { total ->
                intent {
                    reduce {
                        val folderInfo = state.folderInfo.copy(postCount = total)
                        state.copy(folderInfo = folderInfo)
                    }
                }
            },
        ).cachedIn(viewModelScope).map { pagedData ->
            pagedData.map { savedLinkInfo -> savedLinkInfo.toUiModel() }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = PagingData.empty(),
        ).collectLatest { pagingData ->
            _feedListState.emit(pagingData)
        }
    }

    /**
     * default folder links
     */
    private fun getSavedLinkFromDefaultFolder(
        order: String = StorageDetailSort.ASC.name,
        favorite: Boolean = false,
        isRead: Boolean? = null,
    ) = viewModelScope.doraLaunch {
        val pagingData =
            getPostsUseCase.invoke(
                order = order,
                favorite = favorite,
                isRead = isRead,
                totalCount = { total ->
                    intent {
                        reduce {
                            val folderInfo = state.folderInfo.copy(postCount = total)
                            state.copy(folderInfo = folderInfo)
                        }
                    }
                },
            )
                .cachedIn(viewModelScope).map { pagedData ->
                    pagedData.map { savedLinkInfo -> savedLinkInfo.toUiModel() }
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.Lazily,
                    initialValue = PagingData.empty(),
                ).collectLatest { pagingData ->
                    _feedListState.value = pagingData
                }
    }

    /**
     * 탭 변환
     */
    fun changeSelectedTabIdx(selectedIdx: Int) = viewModelScope.launch {
        intent {
            val isRead = if (selectedIdx == 0) null else false
            fetchSavedLinkFromType(
                type = state.folderInfo.folderType,
                folderId = state.folderInfo.folderId,
                order = state.isLatestSort.name,
                isRead = isRead,
            )
            reduce {
                state.copy(tabInfo = state.tabInfo.copy(selectedTabIdx = selectedIdx))
            }
        }
    }

    /**
     * 정렬 변환
     */
    fun clickFeedSort(item: StorageDetailSort) = viewModelScope.launch {
        intent {
            val isRead = if (state.tabInfo.selectedTabIdx == 0) null else false
            fetchSavedLinkFromType(
                type = state.folderInfo.folderType,
                folderId = state.folderInfo.folderId,
                order = item.name,
                isRead = isRead,
            )
            reduce {
                state.copy(
                    isLatestSort = item,
                )
            }
        }
    }

    /**
     * 즐겨찾기 추가 - 낙관적 업데이트 적용
     */
    fun addFavoriteItem(postId: String, isFavorite: Boolean) = viewModelScope.doraLaunch {
        _feedListState.value = feedListState.value.map { item ->
            if (item.postId == postId) {
                item.copy(isFavorite = !isFavorite)
            } else {
                item
            }
        }
        val isSuccessFavorite = patchPostInfoUseCase(postId = postId, postInfo = PostInfo(isFavorite = !isFavorite)).isSuccess
        if (!isSuccessFavorite) {
            _feedListState.value = feedListState.value.map { item ->
                if (item.postId == postId) {
                    item.copy(isFavorite = isFavorite)
                } else {
                    item
                }
            }
        }
    }

    /**
     * 폴더 삭제
     */
    fun deleteFolder(folderId: String?) = viewModelScope.doraLaunch {
        folderId?.let { id ->
            val isSuccessDelete = deleteFolderUseCase(folderId = id)
            if (isSuccessDelete.isSuccess) {
                setVisibleDialog(false)
                intent { postSideEffect(StorageDetailSideEffect.NavigateToHome) }
            } else {
                // TODO - 에러처리
            }
        }
    }

    /**
     * 링크 삭제
     */
    fun deletePost(postId: String) = viewModelScope.doraLaunch {
        val isSuccessDeleted = deletePostUseCase(postId).isSuccess
        setVisibleDialog(false)
        if (isSuccessDeleted) {
            _feedListState.value = feedListState.value.filter { item -> item.postId != postId }
        }
    }

    /**
     * 현재 폴더 리스트 가져오기
     */
    fun getCustomFolderList() = viewModelScope.doraLaunch {
        val folderList = getFolderListUseCase()
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
     * 링크 폴더 이동
     */
    fun moveFolder(postId: String, folderId: String) = viewModelScope.doraLaunch {
        val isSuccess = changePostFolderUseCase(postId = postId, folderId = folderId).isSuccess
        setVisibleMovingFolderBottomSheet(false)
        if (isSuccess) {
            intent { postSideEffect(StorageDetailSideEffect.RefreshPagingList) }
        }
    }

    fun setVisibleMoreButtonBottomSheet(visible: Boolean) = intent {
        val bottomSheet = when (state.editActionType) {
            EditActionType.FolderEdit -> {
                state.moreBottomSheetState.copy(
                    isShowMoreButtonSheet = visible,
                    firstItem = R.string.remove_dialog_folder_title,
                    secondItem = R.string.rename_folder_bottom_sheet_title,
                )
            }
            EditActionType.LinkEdit -> {
                state.moreBottomSheetState.copy(
                    isShowMoreButtonSheet = visible,
                    firstItem = R.string.remove_dialog_title,
                    secondItem = R.string.moving_folder_dialog_title,
                )
            }
        }
        reduce {
            state.copy(moreBottomSheetState = bottomSheet)
        }
    }

    fun setVisibleDialog(visible: Boolean) = intent {
        val dialogState = when (state.editActionType) {
            EditActionType.FolderEdit -> {
                state.editDialogState.copy(
                    isShowDialog = visible,
                    dialogTitle = R.string.remove_dialog_folder_title,
                    dialogCont = R.string.remove_dialog_folder_cont,
                )
            }
            EditActionType.LinkEdit -> {
                state.editDialogState.copy(
                    isShowDialog = visible,
                    dialogTitle = R.string.remove_dialog_title,
                    dialogCont = R.string.remove_dialog_content,
                )
            }
        }
        reduce {
            state.copy(editDialogState = dialogState)
        }
    }

    fun setActionType(type: EditActionType, postId: String = "") = intent {
        reduce { state.copy(editActionType = type, currentClickPostId = postId) }
    }

    fun updateSelectFolderId(folderId: String) = intent {
        reduce { state.copy(changeClickFolderId = folderId) }
    }

    fun moveToEditFolderName(folderId: String?) = intent {
        postSideEffect(StorageDetailSideEffect.NavigateToFolderManage(itemId = folderId.orEmpty()))
    }

    fun setVisibleMovingFolderBottomSheet(visible: Boolean, isNavigate: Boolean = false) = intent {
        reduce {
            state.copy(isShowMovingFolderSheet = visible)
        }
        if (isNavigate) postSideEffect(StorageDetailSideEffect.NavigateToFolderManage(itemId = state.currentClickPostId))
    }
}
