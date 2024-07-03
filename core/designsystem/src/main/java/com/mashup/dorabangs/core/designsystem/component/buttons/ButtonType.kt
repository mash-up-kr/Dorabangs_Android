package com.mashup.dorabangs.core.designsystem.component.buttons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mashup.dorabangs.core.designsystem.theme.BtnMaxColorTokens
import com.mashup.dorabangs.core.designsystem.theme.BtnMaxRoundTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens

object DoraButtons : ButtonType {

    @Composable
    override fun DoraBtnMaxFull(
        modifier: Modifier,
        buttonText: String,
        enabled: Boolean,
        onClickButton: () -> Unit,
    ) = DoraButton(
        modifier = modifier,
        buttonText = buttonText,
        textStyle = DoraTypoTokens.base2Medium,
        enabled = enabled,
        radius = BtnMaxRoundTokens.FullButtonWidthRadius,
        containerColor = BtnMaxColorTokens.ContainerColor1,
        contentColor = BtnMaxColorTokens.ContentColor1,
        disabledContainerColor = BtnMaxColorTokens.ContainerColor1_Off,
        disabledContentColor = BtnMaxColorTokens.ContentColor_1Off,
        onClickButton = onClickButton,
    )

    @Composable
    override fun DoraMediumConfirmBtn(
        modifier: Modifier,
        buttonText: String,
        onClickButton: () -> Unit,
    ) = DoraButton(
        modifier = modifier,
        buttonText = buttonText,
        textStyle = DoraTypoTokens.caption3Medium,
        enabled = true,
        radius = BtnMaxRoundTokens.MediumButtonWidthRadius,
        containerColor = BtnMaxColorTokens.ContainerColor1,
        contentColor = BtnMaxColorTokens.ContentColor1,
        disabledContainerColor = BtnMaxColorTokens.ContainerColor1_Off,
        disabledContentColor = BtnMaxColorTokens.ContentColor_1Off,
        onClickButton = onClickButton,
    )

    @Composable
    override fun DoraMediumDismissBtn(
        modifier: Modifier,
        buttonText: String,
        onClickButton: () -> Unit,
    ) = DoraButton(
        modifier = modifier,
        buttonText = buttonText,
        textStyle = DoraTypoTokens.caption3Medium,
        enabled = true,
        radius = BtnMaxRoundTokens.MediumButtonWidthRadius,
        containerColor = BtnMaxColorTokens.ContainerColor2,
        contentColor = BtnMaxColorTokens.ContentColor2,
        disabledContainerColor = BtnMaxColorTokens.ContainerColor1_Off,
        disabledContentColor = BtnMaxColorTokens.ContentColor_1Off,
        onClickButton = onClickButton,
    )

    @Composable
    override fun DoraSmallConfirmBtn(
        modifier: Modifier,
        buttonText: String,
        onClickButton: () -> Unit,
    ) = DoraButton(
        modifier = modifier,
        buttonText = buttonText,
        textStyle = DoraTypoTokens.caption3Medium,
        enabled = true,
        radius = BtnMaxRoundTokens.FullButtonWidthRadius,
        containerColor = BtnMaxColorTokens.ContainerColor1,
        contentColor = BtnMaxColorTokens.ContentColor1,
        disabledContainerColor = BtnMaxColorTokens.ContainerColor1_Off,
        disabledContentColor = BtnMaxColorTokens.ContentColor_1Off,
        onClickButton = onClickButton,
    )

    @Composable
    override fun DoraColorFullMaxBtn(
        modifier: Modifier,
        buttonText: String,
        enabled: Boolean,
        onClickButton: () -> Unit,
    ) = DoraButton(
        modifier = modifier,
        buttonText = buttonText,
        textStyle = DoraTypoTokens.caption1Medium,
        enabled = enabled,
        radius = BtnMaxRoundTokens.FullButtonWidthRadius,
        containerColor = BtnMaxColorTokens.ContainerColor1,
        contentColor = BtnMaxColorTokens.ContentColor1,
        disabledContainerColor = BtnMaxColorTokens.ContainerColor1_Off,
        disabledContentColor = BtnMaxColorTokens.ContentColor_1Off,
        onClickButton = onClickButton,
    )
}

sealed interface ButtonType {

    @Composable
    fun DoraBtnMaxFull(
        modifier: Modifier,
        buttonText: String,
        enabled: Boolean,
        onClickButton: () -> Unit,
    )

    @Composable
    fun DoraMediumConfirmBtn(
        modifier: Modifier,
        buttonText: String,
        onClickButton: () -> Unit,
    )

    @Composable
    fun DoraMediumDismissBtn(
        modifier: Modifier,
        buttonText: String,
        onClickButton: () -> Unit,
    )

    @Composable
    fun DoraSmallConfirmBtn(
        modifier: Modifier,
        buttonText: String,
        onClickButton: () -> Unit,
    )

    @Composable
    fun DoraColorFullMaxBtn(
        modifier: Modifier,
        buttonText: String,
        enabled: Boolean,
        onClickButton: () -> Unit,
    )
}
