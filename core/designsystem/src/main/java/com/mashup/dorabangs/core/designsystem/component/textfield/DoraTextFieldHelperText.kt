package com.mashup.dorabangs.core.designsystem.component.textfield

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
import com.mashup.dorabangs.core.designsystem.theme.TextFieldHelperTextColorTokens

@Composable
fun DoraTextFieldHelperText(
    enabled: Boolean,
    helperText: String,
    modifier: Modifier = Modifier,
) {
    if (enabled) {
        Text(
            modifier = modifier.height(height = 14.dp),
            text = helperText,
            color = TextFieldHelperTextColorTokens.LabelColor,
            style = DoraTypoTokens.SMedium,
        )
    } else {
        Spacer(
            modifier = Modifier.height(height = 14.dp),
        )
    }
}
