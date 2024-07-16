package com.mashup.dorabangs.core.designsystem.component.folder.icnewfolder

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Square
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.StrokeJoin.Companion.Round
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val NewFolder.IcNewFolder: ImageVector
    get() {
        if (_ic != null) {
            return _ic!!
        }
        _ic = Builder(
            name = "Ic", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp, viewportWidth =
            24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF121212)),
                strokeLineWidth = 2.0f, strokeLineCap = Square, strokeLineJoin = Round,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(12.0f, 8.3276f)
                verticalLineTo(15.6536f)
            }
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF121212)),
                strokeLineWidth = 2.0f, strokeLineCap = Square, strokeLineJoin = Round,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(15.667f, 11.9907f)
                horizontalLineTo(8.334f)
            }
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF121212)),
                strokeLineWidth = 2.0f, strokeLineCap = Square, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(21.25f, 14.113f)
                verticalLineTo(2.75f)
                horizontalLineTo(2.75f)
                verticalLineTo(21.25f)
                horizontalLineTo(21.25f)
                verticalLineTo(18.159f)
            }
        }
            .build()
        return _ic!!
    }

private var _ic: ImageVector? = null
