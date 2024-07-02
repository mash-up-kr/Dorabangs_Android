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
    clipboardManager: ClipboardManager,
    snackBarHostState: SnackbarHostState,
    showSnackBarWithText: (String) -> Unit,
    hideSnackBar: () -> Unit,
    action: (String) -> Unit,
    dismissAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LifecycleResumeEffect(key1 = clipboardManager) {
        runCatching {
            view.post {
                val clipboardText = clipboardManager.getText()?.text.orEmpty()
                showSnackBarWithText(clipboardText)
            }
        }
        onPauseOrDispose {
            hideSnackBar()
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
