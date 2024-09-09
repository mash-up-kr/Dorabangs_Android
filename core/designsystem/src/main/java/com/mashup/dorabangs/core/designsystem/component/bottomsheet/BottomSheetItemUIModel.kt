package com.mashup.dorabangs.core.designsystem.component.bottomsheet

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens

data class BottomSheetItemUIModel(
    @DrawableRes val icon: Int? = null,
    val itemName: String,
    val textStyle: TextStyle = DoraTypoTokens.caption3Normal,
    val color: Color = DoraColorTokens.Black,
)

data class SelectableBottomSheetItemUIModel(
    @DrawableRes val icon: Int? = null,
    val id: String = "",
    val itemName: String,
    val isSelected: Boolean,
    val textStyle: TextStyle = DoraTypoTokens.caption3Normal,
    val selectedTextStyle: TextStyle = DoraTypoTokens.caption3Bold,
    val color: Color = DoraColorTokens.G8,
)
