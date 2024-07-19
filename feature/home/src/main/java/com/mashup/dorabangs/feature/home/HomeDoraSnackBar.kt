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

// 1. https://www.naver.com -> 잘 입력했을 때 그대로
// 2. http://www.naver.com -> http로 입력했을 때
// 3. www.naver.com -> 아무것도 없이 들어왔을 때
fun String.checkAndReplacePrefix(): String {
    if (startsWith("https")) return this
    if (startsWith("http")) return this
    if (contains("https").not() && contains("http").not()) return "https://${this}"
    return ""
}