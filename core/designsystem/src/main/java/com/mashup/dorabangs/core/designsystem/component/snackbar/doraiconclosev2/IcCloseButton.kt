package com.mashup.dorabangs.core.designsystem.component.snackbar.doraiconclosev2

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.component.snackbar.DoraCloseButton

public val DoraCloseButton.IC_CLOSE_BUTTON: ImageVector
    get() {
        if (_ic != null) {
            return _ic!!
        }
        _ic = Builder(
            name = "Ic",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth =
            24.0f,
            viewportHeight = 24.0f,
        ).apply {
            path(
                fill = SolidColor(Color(0x00000000)),
                stroke = SolidColor(Color(0xFF707276)),
                strokeLineWidth = 1.5f,
                strokeLineCap = Round,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(6.0f, 6.5f)
                lineTo(18.0f, 18.5f)
            }
            path(
                fill = SolidColor(Color(0x00000000)),
                stroke = SolidColor(Color(0xFF707276)),
                strokeLineWidth = 1.5f,
                strokeLineCap = Round,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(18.0f, 6.5f)
                lineTo(6.0f, 18.5f)
            }
        }
            .build()
        return _ic!!
    }

private var _ic: ImageVector? = null
