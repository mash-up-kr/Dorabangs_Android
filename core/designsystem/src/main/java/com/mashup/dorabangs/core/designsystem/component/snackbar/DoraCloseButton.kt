package com.mashup.dorabangs.core.designsystem.component.snackbar

import androidx.compose.ui.graphics.vector.ImageVector
import com.mashup.dorabangs.core.designsystem.component.snackbar.doraiconclosev2.IC_CLOSE_BUTTON
import kotlin.collections.List as ____KtList

public object DoraCloseButton

private var __AllIcons: ____KtList<ImageVector>? = null

public val DoraCloseButton.AllIcons: ____KtList<ImageVector>
    get() {
        if (__AllIcons != null) {
            return __AllIcons!!
        }
        __AllIcons = listOf(IC_CLOSE_BUTTON)
        return __AllIcons!!
    }
