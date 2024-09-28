package com.mashup.dorabangs.feature.home

import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.component.bottomsheet.DoraBottomSheet
import com.mashup.dorabangs.core.designsystem.component.dialog.DoraDialog
import com.mashup.dorabangs.core.designsystem.component.toast.DoraToast
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.feature.home.HomeState.Companion.toSelectBottomSheetModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun HomeRoute(
    navigateToCreateFolder: () -> Unit,
    navigateToHomeTutorial: () -> Unit,
    navigateToWebView: (String) -> Unit,
    navigateToClassification: () -> Unit,
    navigateToSaveScreenWithLink: (String) -> Unit,
    navigateToSaveScreenWithoutLink: () -> Unit,
    navigateToUnreadStorageDetail: (Folder) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState(),
    isShowToast: Boolean = false,
    viewModel: HomeViewModel = hiltViewModel(),
    scope: CoroutineScope = rememberCoroutineScope(),
) {
    val state by viewModel.collectAsState()

    val copiedUrlSnackBarHostState by remember { mutableStateOf(SnackbarHostState()) }
    val toastSnackBarHostState by remember { mutableStateOf(SnackbarHostState()) }

    LoadScrollCache(
        state = state,
        scrollState = scrollState,
        scrollCache = viewModel.scrollCache,
    )

    LifecycleEventEffect(Lifecycle.Event.ON_START) {
        if (state.isNeedToRefreshOnStart) {
            viewModel.setAIClassificationCount()
            viewModel.initPostList()
            viewModel.updateFolderList()
            viewModel.setIsNeedToRefreshOnStart(false)
        }

        if (isShowToast && state.hasShowToastState.not()) {
            viewModel.updateToastState("${state.changeFolderName}(으)로 이동했어요.")
            viewModel.updateHasShowToast(true)
        }
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is HomeSideEffect.ShowCopiedUrlSnackBar -> {
                scope.launch {
                    copiedUrlSnackBarHostState.showSnackbar(
                        message = sideEffect.copiedText,
                        duration = SnackbarDuration.Indefinite,
                    )
                }
            }

            is HomeSideEffect.HideSnackBar -> {
                copiedUrlSnackBarHostState.currentSnackbarData?.dismiss()
            }

            is HomeSideEffect.NavigateToCreateFolder -> {
                viewModel.updateEditType(isEditPostFolder = true)
                navigateToCreateFolder()
            }
            is HomeSideEffect.ShowToastSnackBar -> {
                scope.launch { toastSnackBarHostState.showSnackbar(sideEffect.toastMsg) }
            }
            else -> {}
        }
    }

    Box {
        HomeScreen(
            state = state,
            modifier = modifier,
            scrollState = scrollState,
            onClickChip = { index ->
                if (index != state.selectedIndex) {
                    viewModel.changeSelectedTabIdx(index, scrollState.firstVisibleItemIndex)
                }
            },
            onClickMoreButton = { postId, folderId ->
                viewModel.updateSelectedPostItem(postId = postId, folderId)
                viewModel.setVisibleMoreButtonBottomSheet(true)
            },
            onClickBookMarkButton = viewModel::updateFavoriteItem,
            onClickCardItem = { cardInfo ->
                viewModel.updateReadAt(cardInfo)
                navigateToWebView(cardInfo.url)
            },
            onReachedBottom = viewModel::loadMore,
            navigateToClassification = {
                navigateToClassification.invoke()
                viewModel.setIsNeedToRefreshOnStart(true)
            },
            navigateSaveScreenWithoutLink = {
                navigateToSaveScreenWithoutLink()
                viewModel.setIsNeedToRefreshOnStart(true)
            },
            navigateToHomeTutorial = navigateToHomeTutorial,
            navigateToUnreadStorageDetail = {
                val folder = state.allFolder ?: return@HomeScreen
                navigateToUnreadStorageDetail(folder)
                viewModel.setIsNeedToRefreshOnStart(true)
            },
            requestUpdate = viewModel::updatePost,
        )

        HomeSideEffectUI(
            state = state,
            snackBarHostState = copiedUrlSnackBarHostState,
            toastSnackBarHostState = toastSnackBarHostState,
            navigateToSaveScreenWithLink = navigateToSaveScreenWithLink,
            setLocalCopiedUrl = viewModel::setLocalCopiedUrl,
            hideSnackBar = viewModel::hideSnackBar,
            setVisibleMoreButtonBottomSheet = viewModel::setVisibleMoreButtonBottomSheet,
            setVisibleMovingFolderBottomSheet = viewModel::setVisibleMovingFolderBottomSheet,
            getCustomFolderList = viewModel::getCustomFolderList,
            updateSelectFolderId = viewModel::updateSelectFolderId,
            deletePost = viewModel::deletePost,
            moveFolder = viewModel::moveFolder,
            getLocalCopiedUrl = viewModel::getLocalCopiedUrl,
            setVisibleDialog = viewModel::setVisibleDialog,
            showSnackBar = viewModel::showSnackBar,
        )
    }
}

@Composable
fun BoxScope.HomeSideEffectUI(
    state: HomeState,
    snackBarHostState: SnackbarHostState,
    toastSnackBarHostState: SnackbarHostState,
    navigateToSaveScreenWithLink: (String) -> Unit,

    setLocalCopiedUrl: (String) -> Unit,
    getLocalCopiedUrl: suspend () -> String?,
    showSnackBar: (String) -> Unit,
    hideSnackBar: () -> Unit,
    setVisibleMoreButtonBottomSheet: (Boolean) -> Unit,
    setVisibleMovingFolderBottomSheet: (Boolean, Boolean) -> Unit,
    setVisibleDialog: (Boolean) -> Unit,
    getCustomFolderList: () -> Unit,
    updateSelectFolderId: (String, String) -> Unit,
    deletePost: (String) -> Unit,
    moveFolder: (String, String, String) -> Unit,

    view: View = LocalView.current,
    clipboardManager: ClipboardManager = LocalClipboardManager.current,
) {
    HomeDoraSnackBar(
        modifier = Modifier.align(Alignment.BottomCenter),
        text = state.clipBoardState.copiedText,
        onAction = { url ->
            setLocalCopiedUrl(url)
            if (state.clipBoardState.isValidUrl) {
                navigateToSaveScreenWithLink(url)
            }
        },
        snackBarHostState = snackBarHostState,
        view = view,
        clipboardManager = clipboardManager,
        lastCopiedText = { getLocalCopiedUrl().orEmpty() },
        hideSnackBar = hideSnackBar,
        showSnackBarWithText = showSnackBar,
        dismissAction = { url ->
            setLocalCopiedUrl(url)
            hideSnackBar()
        },
    )

    DoraBottomSheet.MoreButtonBottomSheet(
        modifier = Modifier.height(320.dp),
        isShowSheet = state.isShowMoreButtonSheet,
        firstItemName = R.string.more_button_bottom_sheet_remove_link,
        secondItemName = R.string.more_button_bottom_sheet_moving_folder,
        onClickDeleteLinkButton = {
            setVisibleMoreButtonBottomSheet(false)
            setVisibleDialog(true)
        },
        onClickMoveFolderButton = {
            setVisibleMoreButtonBottomSheet(false)
            getCustomFolderList()
        },
        onDismissRequest = { setVisibleMoreButtonBottomSheet(false) },
    )

    DoraBottomSheet.MovingFolderBottomSheet(
        modifier = Modifier,
        isShowSheet = state.isShowMovingFolderSheet,
        isBtnEnabled = state.selectedFolderId != state.changeFolderId,
        folderList = state.folderList.toSelectBottomSheetModel(
            state.changeFolderId.ifEmpty {
                state.selectedFolderId.also {
                    updateSelectFolderId(it, "")
                }
            },
        ),
        onDismissRequest = {
            updateSelectFolderId(state.selectedFolderId, "")
            setVisibleMovingFolderBottomSheet(false, false)
        },
        onClickCreateFolder = {
            setVisibleMovingFolderBottomSheet(false, true)
        },
        onClickMoveFolder = { selectFolder ->
            selectFolder?.let { updateSelectFolderId(selectFolder.id, selectFolder.itemName) }
        },
        onClickCompleteButton = { moveFolder(state.selectedPostId, state.changeFolderId, state.changeFolderName) },
    )

    DoraDialog(
        isShowDialog = state.isShowDialog,
        title = stringResource(R.string.remove_dialog_title),
        content = stringResource(R.string.remove_dialog_content),
        confirmBtnText = stringResource(R.string.remove_dialog_confirm),
        disMissBtnText = stringResource(R.string.remove_dialog_cancil),
        onDisMissRequest = { setVisibleDialog(false) },
        onClickConfirmBtn = { deletePost(state.selectedPostId) },
    )

    DoraToast(
        text = state.toastState.text,
        toastStyle = state.toastState.toastStyle,
        snackBarHostState = toastSnackBarHostState,
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 20.dp),
    )
}

@Composable
fun LoadScrollCache(
    state: HomeState,
    scrollState: LazyListState,
    scrollCache: Map<Int, Int>,
) {
    var prevFolderIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(state.postList.size) {
        if (state.selectedIndex != prevFolderIndex) {
            scrollState.scrollToItem(scrollCache[state.selectedIndex] ?: 0)
            prevFolderIndex = state.selectedIndex
        }
    }
}
