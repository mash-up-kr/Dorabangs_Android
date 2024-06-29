package com.mashup.dorabangs.core.designsystem.component.textfield

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
import com.mashup.dorabangs.core.designsystem.theme.TextFieldErrorLabelColorTokens

@Composable
fun DoraTextFieldErrorLabel(
    errorText: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = errorText,
        color = TextFieldErrorLabelColorTokens.LabelColor,
        style = DoraTypoTokens.SMedium,
    )
}
