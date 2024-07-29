package com.mashup.dorabangs.feature.storage.storagedetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.component.bottomsheet.DoraBottomSheet
import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel
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

val MinToolbarHeight = 96.dp
val MaxToolbarHeight = 161.dp

@Composable
fun StorageDetailRoute(
    folderItem: Folder,
    navigateToHome: (Boolean) -> Unit,
    navigateToFolderManager: (String, EditActionType) -> Unit,
    onClickBackIcon: () -> Unit,
    modifier: Modifier = Modifier,
    isVisibleBottomSheet: Boolean = false,
    isChangeData: Boolean = false,
    storageDetailViewModel: StorageDetailViewModel = hiltViewModel(),
) {
    val listState = rememberLazyListState()
    val overlapHeightPx = with(LocalDensity.current) { MaxToolbarHeight.toPx() - MinToolbarHeight.toPx() }
    val state by storageDetailViewModel.collectAsState()
    val toastSnackBarHostState by remember { mutableStateOf(SnackbarHostState()) }
    val scope = rememberCoroutineScope()
    val linksPagingList = state.pagingList.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        storageDetailViewModel.setFolderInfo(folderItem)

        if (isChangeData) {
            if (state.editActionType == EditActionType.FolderEdit) {
                storageDetailViewModel.getFolderInfoById(state.folderInfo.folderId.orEmpty())
            } else
                linksPagingList.refresh()
        }
        storageDetailViewModel.setVisibleMovingFolderBottomSheet(isVisibleBottomSheet)
    }

    val isCollapsed: Boolean by remember {
        derivedStateOf {
            val isFirstItemHidden = listState.firstVisibleItemScrollOffset > overlapHeightPx
            isFirstItemHidden || listState.firstVisibleItemIndex > 0
        }
    }

    storageDetailViewModel.collectSideEffect { sideEffect ->
        handleSideEffect(
            sideEffect = sideEffect,
            navigateToHome = navigateToHome,
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
            isCollapsed = isCollapsed,
            onClickBackIcon = onClickBackIcon,
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
            onDismissRequest = { storageDetailViewModel.setVisibleMovingFolderBottomSheet(false) },
            onClickCreateFolder = {
                storageDetailViewModel.setVisibleMovingFolderBottomSheet(
                    visible = false,
                    isNavigate = true,
                )
            },
            onClickMoveFolder = { selectFolderId -> storageDetailViewModel.updateSelectFolderId(selectFolderId) },
            btnEnable = state.folderInfo.folderId != state.changeClickFolderId,
            onClickCompleteButton = {
                storageDetailViewModel.moveFolder(
                    postId = state.currentClickPostId,
                    folderId = state.changeClickFolderId,
                )
            },
        )
        DoraToast(
            text = state.toastState.text,
            toastStyle = state.toastState.toastStyle,
            snackBarHostState = toastSnackBarHostState,
            modifier = modifier.align(Alignment.BottomCenter).padding(bottom = 20.dp),
        )
    }
}

private fun handleSideEffect(
    sideEffect: StorageDetailSideEffect,
    navigateToHome: (Boolean) -> Unit,
    navigateToFolderManager: (String) -> Unit,
    refreshPagingList: () -> Unit,
    showToastSnackBar: () -> Unit,
) {
    when (sideEffect) {
        is StorageDetailSideEffect.NavigateToHome -> navigateToHome(true)
        is StorageDetailSideEffect.NavigateToFolderManage -> navigateToFolderManager(sideEffect.itemId)
        is StorageDetailSideEffect.RefreshPagingList -> refreshPagingList()
        is StorageDetailSideEffect.ShowToastSnackBarRenameFolder -> showToastSnackBar()
    }
}

@Composable
fun StorageDetailScreen(
    linksPagingList: LazyPagingItems<FeedCardUiModel>,
    onClickBookMarkButton: (String, Boolean) -> Unit,
    onClickBackIcon: () -> Unit,
    onClickTabItem: (Int) -> Unit,
    onClickSortedIcon: (StorageDetailSort) -> Unit,
    onClickActionIcon: () -> Unit,
    onClickMoreButton: (String) -> Unit,
    modifier: Modifier = Modifier,
    state: StorageDetailState = StorageDetailState(),
    listState: LazyListState = rememberLazyListState(),
    isCollapsed: Boolean = false,
) {
    Box(
        modifier = modifier,
    ) {
        StorageDetailCollapsingHeader(
            state = state,
            modifier = Modifier.zIndex(2f),
            isCollapsed = isCollapsed,
            onClickBackIcon = onClickBackIcon,
            onClickTabItem = onClickTabItem,
            onClickActionIcon = onClickActionIcon,
        )
        StorageDetailList(
            listState = listState,
            linksPagingList = linksPagingList,
            state = state,
            onClickBackIcon = onClickBackIcon,
            onClickSortedIcon = onClickSortedIcon,
            onClickTabItem = onClickTabItem,
            onClickBookMarkButton = onClickBookMarkButton,
            onClickActionIcon = onClickActionIcon,
            onClickMoreButton = onClickMoreButton,

        )
    }
}

@Preview
@Composable
fun PreviewStorageDetailScreen() {
    StorageDetailRoute(
        folderItem = Folder(),
        navigateToHome = {},
        navigateToFolderManager = { id, type -> },
        onClickBackIcon = {},
    )
}
