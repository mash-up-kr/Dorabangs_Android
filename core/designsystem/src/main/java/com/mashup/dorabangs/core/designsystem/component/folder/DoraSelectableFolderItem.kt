package com.mashup.dorabangs.core.designsystem.component.folder

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens

data class DoraSelectableFolderItem(
    val vector: ImageVector,
    val itemName: String,
    val isSelected: Boolean,
    val color: Color = DoraColorTokens.Black,
)