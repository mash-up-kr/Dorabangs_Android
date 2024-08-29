package com.dorabangs.share

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@Composable
fun DoraSnackBarWithShareScreen(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState by remember { mutableStateOf(SnackbarHostState()) }

    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch {
            snackBarHostState.showSnackbar(
                message = "",
                duration = SnackbarDuration.Indefinite,
            )
        }
    }
    Box(
        modifier = modifier,
    ) {
        DoraShareSnackBar(
            modifier = Modifier,
            snackBarHostState = snackBarHostState,
            onClick = onClick,
        )
    }
}
