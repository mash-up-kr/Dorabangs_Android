package com.mashup.dorabangs.core.designsystem.component.snackbar.doraiconclose

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val DoraIconClose.CloseCircle: ImageVector
    get() {
        if (_closeCircle != null) {
            return _closeCircle!!
        }
        _closeCircle = Builder(
            name = "CloseCircle",
            defaultWidth = 26.0.dp,
            defaultHeight =
            26.0.dp,
            viewportWidth = 26.0f,
            viewportHeight = 26.0f,
        ).apply {
            path(
                fill = SolidColor(Color(0x00000000)),
                stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 0.5f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(5.0f, 0.75f)
                lineTo(21.0f, 0.75f)
                arcTo(4.25f, 4.25f, 0.0f, false, true, 25.25f, 5.0f)
                lineTo(25.25f, 21.0f)
                arcTo(4.25f, 4.25f, 0.0f, false, true, 21.0f, 25.25f)
                lineTo(5.0f, 25.25f)
                arcTo(4.25f, 4.25f, 0.0f, false, true, 0.75f, 21.0f)
                lineTo(0.75f, 5.0f)
                arcTo(4.25f, 4.25f, 0.0f, false, true, 5.0f, 0.75f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFDEE2E6)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(13.0f, 3.25f)
                curveTo(7.6239f, 3.25f, 3.25f, 7.6239f, 3.25f, 13.0f)
                curveTo(3.25f, 18.3761f, 7.6239f, 22.75f, 13.0f, 22.75f)
                curveTo(18.3761f, 22.75f, 22.75f, 18.3761f, 22.75f, 13.0f)
                curveTo(22.75f, 7.6239f, 18.3761f, 3.25f, 13.0f, 3.25f)
                close()
                moveTo(16.5302f, 15.4698f)
                curveTo(16.6027f, 15.5388f, 16.6608f, 15.6216f, 16.7008f, 15.7133f)
                curveTo(16.7409f, 15.805f, 16.7622f, 15.9039f, 16.7635f, 16.004f)
                curveTo(16.7648f, 16.1041f, 16.746f, 16.2034f, 16.7083f, 16.2961f)
                curveTo(16.6706f, 16.3889f, 16.6147f, 16.4731f, 16.5439f, 16.5439f)
                curveTo(16.4731f, 16.6147f, 16.3889f, 16.6706f, 16.2961f, 16.7083f)
                curveTo(16.2034f, 16.746f, 16.1041f, 16.7648f, 16.004f, 16.7635f)
                curveTo(15.9039f, 16.7622f, 15.805f, 16.7409f, 15.7133f, 16.7008f)
                curveTo(15.6216f, 16.6608f, 15.5388f, 16.6027f, 15.4698f, 16.5302f)
                lineTo(13.0f, 14.0608f)
                lineTo(10.5302f, 16.5302f)
                curveTo(10.3884f, 16.6649f, 10.1995f, 16.7389f, 10.004f, 16.7364f)
                curveTo(9.8084f, 16.7339f, 9.6215f, 16.6551f, 9.4832f, 16.5168f)
                curveTo(9.3449f, 16.3785f, 9.2661f, 16.1916f, 9.2636f, 15.996f)
                curveTo(9.2611f, 15.8005f, 9.3351f, 15.6116f, 9.4698f, 15.4698f)
                lineTo(11.9392f, 13.0f)
                lineTo(9.4698f, 10.5302f)
                curveTo(9.3351f, 10.3884f, 9.2611f, 10.1995f, 9.2636f, 10.004f)
                curveTo(9.2661f, 9.8084f, 9.3449f, 9.6215f, 9.4832f, 9.4832f)
                curveTo(9.6215f, 9.3449f, 9.8084f, 9.2661f, 10.004f, 9.2636f)
                curveTo(10.1995f, 9.2611f, 10.3884f, 9.3351f, 10.5302f, 9.4698f)
                lineTo(13.0f, 11.9392f)
                lineTo(15.4698f, 9.4698f)
                curveTo(15.6116f, 9.3351f, 15.8005f, 9.2611f, 15.996f, 9.2636f)
                curveTo(16.1916f, 9.2661f, 16.3785f, 9.3449f, 16.5168f, 9.4832f)
                curveTo(16.6551f, 9.6215f, 16.7339f, 9.8084f, 16.7364f, 10.004f)
                curveTo(16.7389f, 10.1995f, 16.6649f, 10.3884f, 16.5302f, 10.5302f)
                lineTo(14.0608f, 13.0f)
                lineTo(16.5302f, 15.4698f)
                close()
            }
        }
            .build()
        return _closeCircle!!
    }

private var _closeCircle: ImageVector? = null
