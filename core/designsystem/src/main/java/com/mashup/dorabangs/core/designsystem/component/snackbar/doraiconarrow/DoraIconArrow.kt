package com.mashup.dorabangs.core.designsystem.component.snackbar.doraiconarrow

import androidx.compose.ui.graphics.vector.ImageVector
import kotlin.collections.List as ____KtList

public object DoraIconArrow

private var __AllIcons: ____KtList<ImageVector>? = null

public val DoraIconArrow.AllIcons: ____KtList<ImageVector>
    get() {
        if (__AllIcons != null) {
            return __AllIcons!!
        }
        __AllIcons = listOf(RightArrow)
        return __AllIcons!!
    }
