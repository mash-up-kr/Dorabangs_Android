package com.mashup.dorabangs.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun DorabangsTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme {
        content()
    }
}
