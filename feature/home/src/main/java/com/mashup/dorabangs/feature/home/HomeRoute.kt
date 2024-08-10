package com.mashup.dorabangs.feature.home

import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
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
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.component.bottomsheet.DoraBottomSheet
import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel
import com.mashup.dorabangs.core.designsystem.component.dialog.DoraDialog
import com.mashup.dorabangs.feature.home.HomeState.Companion.toSelectBottomSheetModel
import com.mashup.dorabangs.feature.utils.getCacheKey
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    clipboardManager: ClipboardManager = LocalClipboardManager.current,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToClassification: () -> Unit = {},
    navigateToSaveScreenWithLink: (String) -> Unit = {},
    navigateToSaveScreenWithoutLink: () -> Unit = {},
    navigateToCreateFolder: () -> Unit,
    navigateToHomeTutorial: () -> Unit,
) {
    val snackBarHostState by remember { mutableStateOf(SnackbarHostState()) }
    val state by viewModel.collectAsState()
    val scope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()

    val postList = remember { mutableStateListOf<FeedCardUiModel>() }
    var prevFolderIndex by remember { mutableIntStateOf(0) }

    val reachedBottom: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 && lastVisibleItem?.index == scrollState.layoutInfo.totalItemsCount - 5
        }
    }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom && viewModel.scrollLoading.not()) {
            viewModel.loadMore(state)
        }
    }

    LaunchedEffect(postList.size) {
        if (state.selectedIndex != prevFolderIndex) {
            scrollState.scrollToItem(viewModel.scrollCache[state.selectedIndex] ?: 0)
            prevFolderIndex = state.selectedIndex
        }
    }

    LaunchedEffect(Unit) {
        viewModel.initPostList(state)
    }

    LaunchedEffect(Unit) {
        viewModel.postList.collectLatest { feedList ->
            if (feedList.isEmpty()) return@collectLatest

            val postIdSet = postList.map { it.postId }.toSet()
            val newList = feedList.filter { post -> post.postId !in postIdSet }
            postList.addAll(newList)
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

            is HomeSideEffect.NavigateToCreateFolder -> navigateToCreateFolder()

            is HomeSideEffect.ResetPostList -> postList.clear()

            is HomeSideEffect.UpdatePost -> {
                val post = sideEffect.post
                val index = postList.indexOf(post)
                postList[index] = post
            }

            else -> {}
        }
    }

    Box {
        HomeScreen(
            state = state,
            modifier = modifier,
            postsList = postList,
            scrollState = scrollState,
            onClickChip = { index ->
                postList.clear()
                viewModel.changeSelectedTapIdx(index, scrollState.firstVisibleItemIndex)
            },
            onClickMoreButton = { postId, folderId ->
                viewModel.updateSelectedPostItem(postId = postId, folderId)
                viewModel.setVisibleMoreButtonBottomSheet(true)
            },
            onClickBookMarkButton = { postId, isFavorite -> viewModel.updateFavoriteItem(postId, isFavorite) },
            navigateToClassification = navigateToClassification,
            navigateSaveScreenWithoutLink = navigateToSaveScreenWithoutLink,
            navigateToHomeTutorial = navigateToHomeTutorial,
        )

        HomeDoraSnackBar(
            modifier = Modifier
                .align(Alignment.BottomCenter),
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
            modifier = modifier,
            isShowSheet = state.isShowMovingFolderSheet,
            folderList = state.folderList.toSelectBottomSheetModel(state.changeFolderId.ifEmpty { state.selectedFolderId }),
            onDismissRequest = { viewModel.setVisibleMovingFolderBottomSheet(false) },
            onClickCreateFolder = {
                viewModel.setVisibleMovingFolderBottomSheet(
                    visible = false,
                    isNavigate = true,
                )
            },
            onClickMoveFolder = { selectFolder -> viewModel.updateSelectFolderId(selectFolder) },
            btnEnable = state.selectedFolderId != state.changeFolderId,
            onClickCompleteButton = { viewModel.moveFolder(state.selectedPostId, state.changeFolderId) },
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
    }
}
