package com.mashup.dorabangs.core.designsystem.component.textfield

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
import com.mashup.dorabangs.core.designsystem.theme.ErrorLabelColorTokens

@Composable
fun DoraErrorLabel(
    errorText: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = errorText,
        color = ErrorLabelColorTokens.LabelColor,
        style = DoraTypoTokens.SMedium,
    )
}
