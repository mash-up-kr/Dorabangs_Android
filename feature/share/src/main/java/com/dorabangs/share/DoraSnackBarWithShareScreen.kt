package com.dorabangs.share

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch

@Composable
fun DoraSnackBarWithShareScreen(
    sharedUrl: String,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
) {
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState by remember { mutableStateOf(SnackbarHostState()) }

    LaunchedEffect(key1 = sharedUrl) {
        coroutineScope.launch {
            snackBarHostState.showSnackbar(
                message = "abcde",
                duration = SnackbarDuration.Short,
            ).also { result ->
                if (result == SnackbarResult.ActionPerformed) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(sharedUrl))
                    context.startService(intent)
                } else {
                    println("tjrwn send to api call")
                }
            }
        }
    }


    Scaffold(
        modifier = modifier,
    ) { paddingValues ->
        DoraShareSnackBar(
            modifier = Modifier.padding(paddingValues),
            snackBarHostState = snackBarHostState,
            onClick = {
                // TODO api call
                      println("tjrwn click 했어요")
            },
        )
    }
}