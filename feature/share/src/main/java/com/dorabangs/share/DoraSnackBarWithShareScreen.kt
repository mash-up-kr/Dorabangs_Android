package com.dorabangs.share

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch

@Composable
fun DoraSnackBarWithShareScreen(
    sharedUrl: String,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState by remember { mutableStateOf(SnackbarHostState()) }

    LaunchedEffect(key1 = sharedUrl) {
        coroutineScope.launch {
            snackBarHostState.showSnackbar(
                message = "abcde",
                duration = SnackbarDuration.Indefinite,
            ).also { result ->
                if (result == SnackbarResult.ActionPerformed) {
                    
                } else {
                    println("tjrwn send to api call")
                }
            }
        }
    }
    Box(
        modifier = modifier,
    ) {
        DoraShareSnackBar(
            modifier = Modifier,
            snackBarHostState = snackBarHostState,
            onClick = {
                // TODO api call
                println("tjrwn click 했어요")
            },
        )
    }
}
