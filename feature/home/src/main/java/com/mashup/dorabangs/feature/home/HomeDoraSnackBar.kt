package com.mashup.dorabangs.feature.home

import android.view.View
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.mashup.dorabangs.core.designsystem.component.snackbar.DoraSnackBar

@Composable
fun HomeDoraSnackBar(
    view: View,
    text: String,
    viewModel: HomeViewModel,
    clipboardManager: ClipboardManager,
    snackBarHostState: SnackbarHostState,
    action: () -> Unit,
    dismissAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LifecycleResumeEffect(key1 = clipboardManager) {
        runCatching {
            view.post {
                val clipboardText = clipboardManager.getText()?.text.orEmpty()
                viewModel.showSnackBar(clipboardText)
            }
        }
        onPauseOrDispose {
            viewModel.hideSnackBar()
        }
    }

    DoraSnackBar(
        modifier = modifier,
        text = text,
        snackBarHostState = snackBarHostState,
        action = action,
        dismissAction = dismissAction,
    )
}
