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
import com.mashup.dorabangs.core.designsystem.component.chips.FeedUiModel
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
import com.mashup.dorabangs.feature.storage.storagedetail.model.FeedCacheKeyType
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailSideEffect
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailSort
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailState
import com.mashup.dorabangs.feature.storage.storagedetail.model.toPost
import com.mashup.dorabangs.feature.storage.storagedetail.model.toSavedLinkDetailInfo
import com.mashup.dorabangs.feature.storage.storagedetail.model.toUiModel
import com.mashup.dorabangs.util.getCacheKey
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
    private val savedStateHandle: SavedStateHandle,
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

    private val _feedListState: MutableStateFlow<PagingData<FeedUiModel.FeedCardUiModel>> = MutableStateFlow(value = PagingData.empty())
    val feedListState: StateFlow<PagingData<FeedUiModel.FeedCardUiModel>> = _feedListState.asStateFlow()

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
            needFetchUpdate = true,
            cacheKey = getCacheKey(FeedCacheKeyType.ALL.name, FeedCacheKeyType.DESC.name),
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
        needFetchUpdate: Boolean = false,
        cacheKey: String = "",
        type: FolderType = FolderType.ALL,
        folderId: String? = "",
        order: String = StorageDetailSort.DESC.name,
        isRead: Boolean? = null,
    ) {
        when (type) {
            FolderType.ALL -> getSavedLinkFromDefaultFolder(
                order = order,
                favorite = false,
                isRead = isRead,
                needFetchUpdate = needFetchUpdate,
                cacheKey = cacheKey,
            )
            FolderType.FAVORITE -> getSavedLinkFromDefaultFolder(
                order = order,
                favorite = true,
                isRead = isRead,
                needFetchUpdate = needFetchUpdate,
                cacheKey = cacheKey,
            )
            else -> getSavedLinkFromCustomFolder(
                folderId = folderId,
                order = order,
                isRead = isRead,
                needFetchUpdate = needFetchUpdate,
                cacheKey = cacheKey,
            )
        }
    }

    /**
     * 사용자 지정 folder links
     */
    private fun getSavedLinkFromCustomFolder(
        needFetchUpdate: Boolean = false,
        cacheKey: String = "",
        folderId: String?,
        order: String = StorageDetailSort.DESC.name,
        limit: Int = 10,
        isRead: Boolean? = null,
    ) = viewModelScope.doraLaunch {
        savedLinksFromFolderUseCase.invoke(
            needFetchUpdate = needFetchUpdate,
            cacheKey = cacheKey,
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
            _feedListState.value = pagingData
        }
    }

    /**
     * default folder links
     */
    private fun getSavedLinkFromDefaultFolder(
        needFetchUpdate: Boolean = false,
        cacheKey: String = "",
        order: String = StorageDetailSort.DESC.name,
        favorite: Boolean = false,
        isRead: Boolean? = null,
    ) = viewModelScope.doraLaunch {
        getPostsUseCase.invoke(
            needFetchUpdate = needFetchUpdate,
            cacheKey = cacheKey,
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

        ).map { pagedData ->
            pagedData.map { savedLinkInfo -> savedLinkInfo.toUiModel() }
        }.cachedIn(viewModelScope)
            .stateIn(
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
            val (isRead, cachedKey) =
                if (selectedIdx == 0) {
                    null to getCacheKey(FeedCacheKeyType.ALL.name, state.isLatestSort.name)
                } else false to getCacheKey(FeedCacheKeyType.UNREAD.name, state.isLatestSort.name)
            fetchSavedLinkFromType(
                type = state.folderInfo.folderType,
                folderId = state.folderInfo.folderId,
                order = state.isLatestSort.name,
                isRead = isRead,
                needFetchUpdate = false,
                cacheKey = cachedKey,
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
            val (isRead, currentTap) =
                if (state.tabInfo.selectedTabIdx == 0) {
                    null to FeedCacheKeyType.ALL.name
                } else false to FeedCacheKeyType.UNREAD.name
            val cacheKey = if (item == StorageDetailSort.ASC) {
                getCacheKey(currentTap, StorageDetailSort.ASC.name)
            } else {
                getCacheKey(currentTap, StorageDetailSort.DESC.name)
            }
            fetchSavedLinkFromType(
                type = state.folderInfo.folderType,
                folderId = state.folderInfo.folderId,
                order = item.name,
                isRead = isRead,
                needFetchUpdate = false,
                cacheKey = cacheKey,
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
    fun addFavoriteItem(postId: String, isFavorite: Boolean, page: Int) = viewModelScope.doraLaunch {
        intent {
            val cachedList = feedListState.value
            var updateItemInfo = FeedUiModel.FeedCardUiModel()
            _feedListState.value = feedListState.value.map { item ->
                if (item.postId == postId) {
                    updateItemInfo = item.copy(isFavorite = !isFavorite)
                    item.copy(isFavorite = !isFavorite)
                } else {
                    item
                }
            }
            val isSuccessFavorite = patchPostInfoUseCase(postId = postId, postInfo = PostInfo(isFavorite = !isFavorite)).isSuccess
            if (isSuccessFavorite) {
                val cacheKey = if (state.tabInfo.selectedTabIdx == 0) {
                    getCacheKey(FeedCacheKeyType.ALL.name, state.isLatestSort.name)
                } else {
                    getCacheKey(FeedCacheKeyType.UNREAD.name, state.isLatestSort.name)
                }
                when (state.folderInfo.folderType) {
                    FolderType.ALL, FolderType.FAVORITE -> {
                        getPostsUseCase.updatePostItem(
                            page = page + 1,
                            cacheKey = cacheKey,
                            cachedKeyList = getUpdateKeyCase(),
                            item = updateItemInfo.toPost(),
                        )
                    }
                    else -> {
                        savedLinksFromFolderUseCase.updatePostItem(
                            page = page + 1,
                            cacheKey = cacheKey,
                            cachedKeyList = getUpdateKeyCase(),
                            item = updateItemInfo.toSavedLinkDetailInfo(),
                        )
                    }
                }
            } else {
                _feedListState.value = cachedList
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

    fun refresh() = viewModelScope.doraLaunch {
        intent {
            val tabPosition = if (state.tabInfo.selectedTabIdx == 0) FeedCacheKeyType.ALL.name else FeedCacheKeyType.UNREAD.name
            fetchSavedLinkFromType(
                type = state.folderInfo.folderType,
                folderId = state.folderInfo.folderId,
                needFetchUpdate = true,
                cacheKey = getCacheKey(tabPosition, state.isLatestSort.name),
            )
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

    private fun getUpdateKeyCase(): List<String> {
        return listOf(
            getCacheKey(FeedCacheKeyType.ALL.name, FeedCacheKeyType.DESC.name),
            getCacheKey(FeedCacheKeyType.UNREAD.name, FeedCacheKeyType.DESC.name),
            getCacheKey(FeedCacheKeyType.ALL.name, FeedCacheKeyType.ASC.name),
            getCacheKey(FeedCacheKeyType.UNREAD.name, FeedCacheKeyType.DESC.name),
        )
    }
}
