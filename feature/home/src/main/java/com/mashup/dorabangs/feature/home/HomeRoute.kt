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

    val snackBarHostState by remember { mutableStateOf(SnackbarHostState()) }
    val toastSnackBarHostState by remember { mutableStateOf(SnackbarHostState()) }

    var prevFolderIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(state.postList.size) {
        if (state.selectedIndex != prevFolderIndex) {
            scrollState.scrollToItem(viewModel.scrollCache[state.selectedIndex] ?: 0)
            prevFolderIndex = state.selectedIndex
        }
    }

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
            is HomeSideEffect.ShowSnackBar -> {
                scope.launch {
                    snackBarHostState.showSnackbar(
                        message = sideEffect.copiedText,
                        duration = SnackbarDuration.Indefinite,
                    )
                }
            }

            is HomeSideEffect.HideSnackBar -> {
                snackBarHostState.currentSnackbarData?.dismiss()
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
            navigateToClassification = navigateToClassification,
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
            viewModel = viewModel,
            snackBarHostState = snackBarHostState,
            toastSnackBarHostState = toastSnackBarHostState,
            navigateToSaveScreenWithLink = navigateToSaveScreenWithLink,
        )
    }
}

@Composable
fun BoxScope.HomeSideEffectUI(
    state: HomeState,
    viewModel: HomeViewModel,
    snackBarHostState: SnackbarHostState,
    toastSnackBarHostState: SnackbarHostState,
    navigateToSaveScreenWithLink: (String) -> Unit,
    view: View = LocalView.current,
    clipboardManager: ClipboardManager = LocalClipboardManager.current,
) {
    HomeDoraSnackBar(
        modifier = Modifier.align(Alignment.BottomCenter),
        text = state.clipBoardState.copiedText,
        onAction = { url ->
            viewModel.setLocalCopiedUrl(url = url)
            if (state.clipBoardState.isValidUrl) {
                navigateToSaveScreenWithLink.invoke(url)
            }
        },
        snackBarHostState = snackBarHostState,
        view = view,
        clipboardManager = clipboardManager,
        lastCopiedText = { viewModel.getLocalCopiedUrl().orEmpty() },
        hideSnackBar = viewModel::hideSnackBar,
        showSnackBarWithText = viewModel::showSnackBar,
        dismissAction = { url ->
            viewModel.setLocalCopiedUrl(url = url)
            viewModel.hideSnackBar()
        },
    )

    DoraBottomSheet.MoreButtonBottomSheet(
        modifier = Modifier.height(320.dp),
        isShowSheet = state.isShowMoreButtonSheet,
        firstItemName = R.string.more_button_bottom_sheet_remove_link,
        secondItemName = R.string.more_button_bottom_sheet_moving_folder,
        onClickDeleteLinkButton = {
            viewModel.setVisibleMoreButtonBottomSheet(false)
            viewModel.setVisibleDialog(true)
        },
        onClickMoveFolderButton = {
            viewModel.setVisibleMoreButtonBottomSheet(false)
            viewModel.getCustomFolderList()
        },
        onDismissRequest = { viewModel.setVisibleMoreButtonBottomSheet(false) },
    )

    DoraBottomSheet.MovingFolderBottomSheet(
        modifier = Modifier,
        isShowSheet = state.isShowMovingFolderSheet,
        isBtnEnable = state.selectedFolderId != state.changeFolderId,
        folderList = state.folderList.toSelectBottomSheetModel(
            state.changeFolderId.ifEmpty {
                state.selectedFolderId.apply {
                    viewModel.updateSelectFolderId(this)
                }
            },
        ),
        onDismissRequest = {
            viewModel.updateSelectFolderId(state.selectedFolderId)
            viewModel.setVisibleMovingFolderBottomSheet(false)
        },
        onClickCreateFolder = {
            viewModel.setVisibleMovingFolderBottomSheet(
                visible = false,
                isNavigate = true,
            )
        },
        onClickMoveFolder = { selectFolder ->
            selectFolder?.let { viewModel.updateSelectFolderId(selectFolder.id, selectFolder.itemName) }
        },
        onClickCompleteButton = { viewModel.moveFolder(state.selectedPostId, state.changeFolderId, state.changeFolderName) },
    )

    DoraDialog(
        isShowDialog = state.isShowDialog,
        title = stringResource(R.string.remove_dialog_title),
        content = stringResource(R.string.remove_dialog_content),
        confirmBtnText = stringResource(R.string.remove_dialog_confirm),
        disMissBtnText = stringResource(R.string.remove_dialog_cancil),
        onDisMissRequest = { viewModel.setVisibleDialog(false) },
        onClickConfirmBtn = { viewModel.deletePost(state.selectedPostId) },
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
