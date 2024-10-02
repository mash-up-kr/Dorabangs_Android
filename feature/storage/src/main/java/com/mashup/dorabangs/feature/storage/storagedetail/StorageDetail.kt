package com.mashup.dorabangs.feature.storage.storagedetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.component.bottomsheet.DoraBottomSheet
import com.mashup.dorabangs.core.designsystem.component.chips.FeedUiModel
import com.mashup.dorabangs.core.designsystem.component.dialog.DoraDialog
import com.mashup.dorabangs.core.designsystem.component.toast.DoraToast
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.feature.storage.storagedetail.model.EditActionType
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailSideEffect
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailSort
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailState
import com.mashup.dorabangs.feature.storage.storagedetail.model.toSelectBottomSheetModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import com.mashup.dorabangs.feature.storage.R as storageR

@Composable
fun StorageDetailRoute(
    folderItem: Folder,
    isUnread: Boolean,
    changeFolderName: String,
    navigateToStorage: (Boolean) -> Unit,
    navigateToFolderManager: (String, EditActionType) -> Unit,
    onClickBackIcon: (Boolean) -> Unit,
    navigateToWebView: (FeedUiModel.FeedCardUiModel) -> Unit,
    modifier: Modifier = Modifier,
    isVisibleBottomSheet: Boolean = false,
    isChangedData: Boolean = false,
    storageDetailViewModel: StorageDetailViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by storageDetailViewModel.collectAsState()
    val toastSnackBarHostState by remember { mutableStateOf(SnackbarHostState()) }
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val linksPagingList = storageDetailViewModel.feedListState.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        if (state.isUnreadChecked.not()) {
            storageDetailViewModel.updateIsUnread(isUnread)
        }
        if (state.folderInfo.folderId.isNullOrEmpty()) {
            storageDetailViewModel.updateChangeData(false)
            storageDetailViewModel.setFolderInfo(folderItem)
        }
        if (isChangedData) {
            storageDetailViewModel.updateChangeData(true)
            if (state.editActionType == EditActionType.FolderEdit) {
                storageDetailViewModel.getFolderInfoById(
                    folderId = state.folderInfo.folderId.orEmpty(),
                    toastMsg = context.getString(storageR.string.toast_rename_folder),
                )
            } else {
                if (changeFolderName.isNotEmpty()) {
                    storageDetailViewModel.updateToastState("$changeFolderName(으)로 이동했어요.")
                }
                storageDetailViewModel.refresh()
            }
        }
        storageDetailViewModel.setVisibleMovingFolderBottomSheet(isVisibleBottomSheet)
    }

    storageDetailViewModel.collectSideEffect { sideEffect ->
        handleSideEffect(
            sideEffect = sideEffect,
            navigateToStorage = navigateToStorage,
            navigateToFolderManager = { id -> navigateToFolderManager(id, state.editActionType) },
            refreshPagingList = { linksPagingList.refresh() },
            showToastSnackBar = {
                scope.launch { toastSnackBarHostState.showSnackbar(state.toastState.text) }
            },
        )
    }
    Box {
        StorageDetailScreen(
            state = state,
            linksPagingList = linksPagingList,
            listState = listState,
            onClickBackIcon = { onClickBackIcon(state.isChangeData) },
            onClickTabItem = storageDetailViewModel::changeSelectedTabIdx,
            onClickSortedIcon = storageDetailViewModel::clickFeedSort,
            onClickBookMarkButton = storageDetailViewModel::addFavoriteItem,
            onClickActionIcon = {
                storageDetailViewModel.setActionType(EditActionType.FolderEdit)
                storageDetailViewModel.setVisibleMoreButtonBottomSheet(true)
            },
            onClickMoreButton = { postId ->
                storageDetailViewModel.setActionType(EditActionType.LinkEdit, postId)
                storageDetailViewModel.setVisibleMoreButtonBottomSheet(true)
            },
            onClickPostItem = { cardInfo ->
                storageDetailViewModel.updateReadAt(cardInfo)
                navigateToWebView(cardInfo)
            },
        )

        DoraBottomSheet.MoreButtonBottomSheet(
            modifier = Modifier.height(320.dp),
            isShowSheet = state.moreBottomSheetState.isShowMoreButtonSheet,
            firstItemName = state.moreBottomSheetState.firstItem,
            secondItemName = state.moreBottomSheetState.secondItem,
            onClickDeleteLinkButton = {
                storageDetailViewModel.setVisibleMoreButtonBottomSheet(false)
                storageDetailViewModel.setVisibleDialog(true)
            },
            onClickMoveFolderButton = {
                storageDetailViewModel.setVisibleMoreButtonBottomSheet(false)
                when (state.editActionType) {
                    EditActionType.FolderEdit -> storageDetailViewModel.moveToEditFolderName(state.folderInfo.folderId)
                    EditActionType.LinkEdit -> storageDetailViewModel.getCustomFolderList()
                }
            },
            onDismissRequest = { storageDetailViewModel.setVisibleMoreButtonBottomSheet(false) },
        )

        DoraDialog(
            isShowDialog = state.editDialogState.isShowDialog,
            title = stringResource(state.editDialogState.dialogTitle),
            content = stringResource(state.editDialogState.dialogCont),
            confirmBtnText = stringResource(R.string.remove_dialog_confirm),
            disMissBtnText = stringResource(R.string.remove_dialog_cancil),
            onDisMissRequest = { storageDetailViewModel.setVisibleDialog(false) },
            onClickConfirmBtn = {
                when (state.editActionType) {
                    EditActionType.FolderEdit -> storageDetailViewModel.deleteFolder(state.folderInfo.folderId)
                    EditActionType.LinkEdit -> storageDetailViewModel.deletePost(state.currentClickPostId)
                }
            },
        )

        DoraBottomSheet.MovingFolderBottomSheet(
            modifier = modifier,
            isShowSheet = state.isShowMovingFolderSheet,
            folderList = state.folderList.toSelectBottomSheetModel(
                state.changeClickFolderId.ifEmpty {
                    val originFolder = state.folderInfo.folderId.orEmpty()
                    storageDetailViewModel.updateSelectFolderId(originFolder)
                    originFolder
                },
            ),
            onDismissRequest = {
                storageDetailViewModel.updateSelectFolderId(state.folderInfo.folderId.orEmpty())
                storageDetailViewModel.setVisibleMovingFolderBottomSheet(false)
            },
            onClickCreateFolder = {
                storageDetailViewModel.setVisibleMovingFolderBottomSheet(
                    visible = false,
                    isNavigate = true,
                )
            },
            onClickMoveFolder = { selectFolder ->
                selectFolder?.let {
                    storageDetailViewModel.updateSelectFolderId(selectFolder.id, selectFolder.itemName)
                }
            },
            isBtnEnabled = state.folderInfo.folderId != state.changeClickFolderId,
            onClickCompleteButton = {
                storageDetailViewModel.moveFolder(
                    postId = state.currentClickPostId,
                    folderId = state.changeClickFolderId,
                    folderName = state.changeClickFolderName,
                )
            },
        )
        DoraToast(
            text = state.toastState.text,
            toastStyle = state.toastState.toastStyle,
            snackBarHostState = toastSnackBarHostState,
            modifier = modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
        )
    }
}

private fun handleSideEffect(
    sideEffect: StorageDetailSideEffect,
    navigateToStorage: (Boolean) -> Unit,
    navigateToFolderManager: (String) -> Unit,
    refreshPagingList: () -> Unit,
    showToastSnackBar: () -> Unit,
) {
    when (sideEffect) {
        is StorageDetailSideEffect.NavigateToHome -> navigateToStorage(true)
        is StorageDetailSideEffect.NavigateToFolderManage -> navigateToFolderManager(sideEffect.itemId)
        is StorageDetailSideEffect.RefreshPagingList -> refreshPagingList()
        is StorageDetailSideEffect.ShowToastSnackBar -> showToastSnackBar()
    }
}

@Composable
fun StorageDetailScreen(
    linksPagingList: LazyPagingItems<FeedUiModel.FeedCardUiModel>,
    onClickBookMarkButton: (FeedUiModel.FeedCardUiModel, Boolean, Int) -> Unit,
    onClickBackIcon: () -> Unit,
    onClickTabItem: (Int) -> Unit,
    onClickSortedIcon: (StorageDetailSort) -> Unit,
    onClickActionIcon: () -> Unit,
    onClickMoreButton: (String) -> Unit,
    onClickPostItem: (FeedUiModel.FeedCardUiModel) -> Unit,
    modifier: Modifier = Modifier,
    state: StorageDetailState = StorageDetailState(),
    listState: LazyListState = rememberLazyListState(),
) {
    Column(
        modifier = modifier,
    ) {
        StorageDetailHeader(
            state = state,
            chipList = state.tabInfo.tabTitleList,
            selectedTabIdx = state.tabInfo.selectedTabIdx,
            onClickBackIcon = onClickBackIcon,
            onClickTabItem = onClickTabItem,
            onClickActionIcon = onClickActionIcon,
        )
        StorageDetailList(
            listState = listState,
            linksPagingList = linksPagingList,
            state = state,
            onClickSortedIcon = onClickSortedIcon,
            onClickBookMarkButton = onClickBookMarkButton,
            onClickMoreButton = onClickMoreButton,
            onClickPostItem = onClickPostItem,

        )
    }
}

@Preview
@Composable
fun PreviewStorageDetailScreen() {
    StorageDetailRoute(
        folderItem = Folder(),
        navigateToStorage = {},
        navigateToFolderManager = { id, type -> },
        onClickBackIcon = {},
        navigateToWebView = {},
        changeFolderName = "",
        isUnread = false,
    )
}
