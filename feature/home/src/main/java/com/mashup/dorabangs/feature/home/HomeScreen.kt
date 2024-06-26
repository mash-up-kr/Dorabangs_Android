package com.mashup.dorabangs.feature.home

import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.AnnotatedString
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.mashup.dorabangs.core.designsystem.component.snackbar.DoraSnackBar
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    clipboardManager: ClipboardManager = LocalClipboardManager.current,
    viewModel: HomeViewModel = hiltViewModel(),
    actionSnackBar: () -> Unit = {},
) {
    var copiedText by remember { mutableStateOf("") }
    var shouldSnackBarShown by remember { mutableStateOf(false) }

    LifecycleResumeEffect(key1 = clipboardManager) {
        kotlin.runCatching {
            view.post {
                copiedText = clipboardManager.getText()?.text.orEmpty()
                shouldSnackBarShown = copiedText.isNotBlank()
            }
        }
        onPauseOrDispose {
            shouldSnackBarShown = false
        }
    }

    Box {
        HomeScreen(
            state = viewModel.collectAsState().value,
            modifier = modifier,
            onClickAddButton = { viewModel.add(1) },
            onClickTestButton = { viewModel.test() },
        )
        if (shouldSnackBarShown) {
            DoraSnackBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                text = copiedText,
                action = actionSnackBar,
                dismissAction = {
                    clipboardManager.setText(AnnotatedString(""))
                    shouldSnackBarShown = false
                },
            )
        }
    }
}

@Composable
fun HomeScreen(
    state: HomeState,
    modifier: Modifier = Modifier,
    onClickAddButton: () -> Unit = {},
    onClickTestButton: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("홈텍스: ${state.number}")
        Button(onClick = onClickAddButton) {
            Text("Add Button")
        }
        Button(onClick = onClickTestButton) {
            Text("도라방스")
        }

        Text(text = "H3 Bold", style = DoraTypoTokens.H3Bold)
        Text(text = "H3 Medium", style = DoraTypoTokens.H3Medium)
        Text(text = "H3 Normal", style = DoraTypoTokens.H3Normal)
    }
}
