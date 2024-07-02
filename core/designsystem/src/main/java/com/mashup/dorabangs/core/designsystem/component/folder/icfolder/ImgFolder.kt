package com.mashup.dorabangs.core.designsystem.component.folder.icfolder

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Folder.ImgFolder: ImageVector
    get() {
        if (_imgFolder != null) {
            return _imgFolder!!
        }
        _imgFolder = Builder(
            name = "ImgFolder", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF4B4C4E)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(2.0f, 5.5f)
                curveTo(2.0f, 4.6716f, 2.6716f, 4.0f, 3.5f, 4.0f)
                horizontalLineTo(8.5f)
                lineTo(10.75f, 6.5f)
                horizontalLineTo(2.0f)
                verticalLineTo(5.5f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFADB5BD)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(2.0f, 6.501f)
                horizontalLineTo(20.0f)
                curveTo(21.1046f, 6.501f, 22.0f, 7.3964f, 22.0f, 8.501f)
                verticalLineTo(17.3581f)
                curveTo(22.0f, 18.4627f, 21.1046f, 19.3581f, 20.0f, 19.3581f)
                horizontalLineTo(4.0f)
                curveTo(2.8954f, 19.3581f, 2.0f, 18.4627f, 2.0f, 17.3581f)
                verticalLineTo(6.501f)
                close()
            }
        }
            .build()
        return _imgFolder!!
    }

private var _imgFolder: ImageVector? = null
