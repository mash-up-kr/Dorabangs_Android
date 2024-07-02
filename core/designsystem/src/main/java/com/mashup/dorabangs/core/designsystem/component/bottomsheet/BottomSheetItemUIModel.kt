package com.mashup.dorabangs.core.designsystem.component.bottomsheet

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens

data class BottomSheetItemUIModel(
    @DrawableRes val icon: Int,
    val itemName: String,
    val color: Color = DoraColorTokens.Black,
)

data class SelectableBottomSheetItemUIModel(
    @DrawableRes val icon: Int,
    val itemName: String,
    val isSelected: Boolean,
)
