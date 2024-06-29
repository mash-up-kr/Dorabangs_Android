package com.mashup.dorabangs.core.designsystem.component.textfield

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
import com.mashup.dorabangs.core.designsystem.theme.TextFieldLabelColorTokens

@Composable
fun DoraTextFieldLabel(
    labelText: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = labelText,
        style = DoraTypoTokens.caption1Medium,
        color = TextFieldLabelColorTokens.LabelColor,
    )
}
