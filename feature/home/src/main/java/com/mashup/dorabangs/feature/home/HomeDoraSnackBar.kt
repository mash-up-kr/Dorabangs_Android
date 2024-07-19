package com.mashup.dorabangs.feature.home

import android.view.View
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.mashup.dorabangs.core.designsystem.component.snackbar.DoraSnackBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeDoraSnackBar(
    view: View,
    text: String,
    clipboardManager: ClipboardManager,
    snackBarHostState: SnackbarHostState,
    lastCopiedText: suspend () -> String,
    showSnackBarWithText: (String) -> Unit,
    hideSnackBar: () -> Unit,
    onAction: (String) -> Unit,
    dismissAction: (String) -> Unit,
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) {
    LifecycleResumeEffect(key1 = clipboardManager) {
        runCatching {
            view.post {
                val clipboardText = clipboardManager.getText()?.text.orEmpty()
                coroutineScope.launch {
                    if (clipboardText != lastCopiedText.invoke()) {
                        showSnackBarWithText(clipboardText)
                    }
                }
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
        onAction = onAction,
        dismissAction = { dismissAction(text) },
    )
}
