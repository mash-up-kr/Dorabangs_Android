package com.mashup.dorabangs.core.designsystem.component.folder

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens

data class DoraSelectableFolderItem(
    @DrawableRes val vector: Int,
    val itemName: String,
    val isSelected: Boolean,
    val color: Color = DoraColorTokens.Black,
)
