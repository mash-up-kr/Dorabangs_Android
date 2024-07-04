package com.mashup.dorabangs.core.designsystem.component.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun DoraButton(
    modifier: Modifier,
    buttonText: String,
    textStyle: TextStyle,
    enabled: Boolean,
    radius: RoundedCornerShape,
    containerColor: Color,
    contentColor: Color,
    disabledContainerColor: Color,
    disabledContentColor: Color,
    onClickButton: () -> Unit,
) {
    Button(
        modifier = modifier,
        colors = ButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor,
        ),
        enabled = enabled,
        shape = radius,
        onClick = onClickButton,
    ) {
        Text(
            text = buttonText,
            style = textStyle,
        )
    }
}

@Preview
@Composable
fun PreviewEnabledDoraBtnMax() {
    DoraButtons.DoraBtnMaxFull(
        modifier = Modifier.fillMaxWidth(),
        enabled = true,
        buttonText = "버튼",
        onClickButton = {},
    )
}

@Preview
@Composable
fun PreviewDisabledDoraBtnMax() {
    DoraButtons.DoraBtnMaxFull(
        modifier = Modifier.fillMaxWidth(),
        enabled = false,
        buttonText = "버튼",
        onClickButton = {},
    )
}

@Preview
@Composable
fun PreviewMediumPositiveBtn() {
    DoraButtons.DoraMediumConfirmBtn(
        modifier = Modifier.fillMaxWidth(0.5f),
        buttonText = "버튼",
        onClickButton = {},
    )
}

@Preview
@Composable
fun PreviewMediumNegativeBtn() {
    DoraButtons.DoraMediumDismissBtn(
        modifier = Modifier.fillMaxWidth(0.5f),
        buttonText = "버튼",
        onClickButton = {},
    )
}

@Preview
@Composable
fun PreviewSmallConfrimBtn() {
    DoraButtons.DoraSmallConfirmBtn(
        modifier = Modifier.padding(horizontal = 30.dp, vertical = 8.dp).width(108.dp),
        buttonText = "버튼",
        onClickButton = {},
    )
}
