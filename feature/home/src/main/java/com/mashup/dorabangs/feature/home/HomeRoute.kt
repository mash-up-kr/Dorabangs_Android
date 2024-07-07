package com.mashup.dorabangs.feature.home

import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.component.bottomsheet.DoraBottomSheet
import com.mashup.dorabangs.core.designsystem.component.dialog.DoraDialog
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect


@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    clipboardManager: ClipboardManager = LocalClipboardManager.current,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToSaveLink: (String) -> Unit = {},
    navigateToClassification: () -> Unit = {},
) {
    val snackBarHostState by remember { mutableStateOf(SnackbarHostState()) }
    val state by viewModel.collectAsState()
    val scope = rememberCoroutineScope()
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

            HomeSideEffect.HideSnackBar -> {
                clipboardManager.setText(AnnotatedString(""))
                snackBarHostState.currentSnackbarData?.dismiss()
            }
        }
    }

    Box {
        HomeScreen(
            state = state,
            modifier = modifier,
            onClickChip = viewModel::changeSelectedTapIdx,
            navigateToClassification = navigateToClassification,
            onClickMoreButton = {
                viewModel.setVisibleMoreButtonBottomSheet(true)
            },
        )

        HomeDoraSnackBar(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            text = state.clipBoardState.copiedText,
            onAction = navigateToSaveLink,
            snackBarHostState = snackBarHostState,
            view = view,
            clipboardManager = clipboardManager,
            hideSnackBar = viewModel::hideSnackBar,
            showSnackBarWithText = viewModel::showSnackBar,
            dismissAction = viewModel::hideSnackBar,
        )

        DoraBottomSheet.MoreButtonBottomSheet(
            modifier = Modifier.height(320.dp),
            isShowSheet = state.isShowMoreButtonSheet,
            onClickDeleteLinkButton = {
                viewModel.setVisibleMoreButtonBottomSheet(false)
                viewModel.setVisibleDialog(true)
            },
            onClickMoveFolderButton = {
                viewModel.setVisibleMoreButtonBottomSheet(false)
                viewModel.setVisibleMovingFolderBottomSheet(true)
            },
            onDismissRequest = { viewModel.setVisibleMoreButtonBottomSheet(false) },
        )

        DoraBottomSheet.MovingFolderBottomSheet(
            modifier = Modifier.height(441.dp),
            isShowSheet = state.isShowMovingFolderSheet,
            folderList = testFolderListData,
            onDismissRequest = { viewModel.setVisibleMovingFolderBottomSheet(false) },
        )

        DoraDialog(
            isShowDialog = state.isShowDialog,
            title = stringResource(R.string.remove_dialog_title),
            content = stringResource(R.string.remove_dialog_content),
            confirmBtnText = stringResource(R.string.remove_dialog_confirm),
            disMissBtnText = stringResource(R.string.remove_dialog_cancil),
            onDisMissRequest = { viewModel.setVisibleDialog(false) },
            onClickConfirmBtn = { viewModel.setVisibleDialog(false) },
        )
    }
}