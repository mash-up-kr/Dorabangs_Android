package com.mashup.dorabangs.feature.home

import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.AnnotatedString
import androidx.hilt.navigation.compose.hiltViewModel
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    clipboardManager: ClipboardManager = LocalClipboardManager.current,
    viewModel: HomeViewModel = hiltViewModel(),
    actionSnackBar: () -> Unit = {},
) {
    val snackBarHostState by remember { mutableStateOf(SnackbarHostState()) }
    val state by viewModel.collectAsState()
    val scope = rememberCoroutineScope()
    viewModel.collectSideEffect {
        when (it) {
            is HomeSideEffect.ShowSnackBar -> {
                scope.launch {
                    snackBarHostState.showSnackbar(
                        message = it.copiedText,
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
            onClickAddButton = { viewModel.add(1) },
            onClickTestButton = { viewModel.test() },
        )

        HomeDoraSnackBar(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            text = state.clipBoardState.copiedText,
            action = actionSnackBar,
            snackBarHostState = snackBarHostState,
            view = view,
            viewModel = viewModel,
            clipboardManager = clipboardManager,
            dismissAction = viewModel::hideSnackBar,
        )
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
