package com.mashup.dorabangs.core.designsystem.component.textfield

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
import com.mashup.dorabangs.core.designsystem.theme.TextFieldColorTokens

@Composable
fun DoraTextCounter(
    counterEnabled: Boolean,
    textLength: Int,
    modifier: Modifier = Modifier,
) {
    if (counterEnabled) {
        Text(
            modifier = modifier,
            text = "$textLength/15",
            color = TextFieldColorTokens.TextCounterColor,
            style = DoraTypoTokens.SNormal,
        )
    }
}

@Composable
@Preview
fun DoraTextCounterPreview() {
    DoraTextCounter(
        counterEnabled = true,
        textLength = 3,
    )
}
