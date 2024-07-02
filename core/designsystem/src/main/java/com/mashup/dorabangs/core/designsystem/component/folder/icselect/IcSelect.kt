package com.mashup.dorabangs.core.designsystem.component.folder.icselect

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val ImgSelect.IcSelect: ImageVector
    get() {
        if (_icSelect != null) {
            return _icSelect!!
        }
        _icSelect = Builder(name = "IcSelect", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(10.0f, 17.0f)
                lineTo(9.2929f, 17.7071f)
                lineTo(10.0f, 18.4142f)
                lineTo(10.7071f, 17.7071f)
                lineTo(10.0f, 17.0f)
                close()
                moveTo(19.7071f, 8.7071f)
                lineTo(20.4142f, 8.0f)
                lineTo(19.0f, 6.5858f)
                lineTo(18.2929f, 7.2929f)
                lineTo(19.7071f, 8.7071f)
                close()
                moveTo(4.2929f, 12.7071f)
                lineTo(9.2929f, 17.7071f)
                lineTo(10.7071f, 16.2929f)
                lineTo(5.7071f, 11.2929f)
                lineTo(4.2929f, 12.7071f)
                close()
                moveTo(10.7071f, 17.7071f)
                lineTo(19.7071f, 8.7071f)
                lineTo(18.2929f, 7.2929f)
                lineTo(9.2929f, 16.2929f)
                lineTo(10.7071f, 17.7071f)
                close()
            }
        }
        .build()
        return _icSelect!!
    }

private var _icSelect: ImageVector? = null
