package com.mashup.dorabangs.core.designsystem.component.snackbar.doraiconarrow

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val DoraIconArrow.RightArrow: ImageVector
    get() {
        if (_rightArrow != null) {
            return _rightArrow!!
        }
        _rightArrow = Builder(
            name = "RightArrow",
            defaultWidth = 20.0.dp,
            defaultHeight = 20.0.dp,
            viewportWidth = 20.0f,
            viewportHeight = 20.0f,
        ).apply {
            path(
                fill = SolidColor(Color(0xFFDEE2E6)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(12.2632f, 9.4381f)
                lineTo(8.8694f, 6.0684f)
                curveTo(8.795f, 5.994f, 8.7064f, 5.9348f, 8.6089f, 5.8945f)
                curveTo(8.5114f, 5.8541f, 8.4067f, 5.8334f, 8.3011f, 5.8334f)
                curveTo(8.1954f, 5.8334f, 8.0908f, 5.8541f, 7.9932f, 5.8945f)
                curveTo(7.8957f, 5.9348f, 7.8072f, 5.994f, 7.7328f, 6.0684f)
                curveTo(7.5837f, 6.2174f, 7.5f, 6.4188f, 7.5f, 6.6287f)
                curveTo(7.5f, 6.8387f, 7.5837f, 7.0401f, 7.7328f, 7.189f)
                lineTo(10.5663f, 10.0023f)
                lineTo(7.7328f, 12.8157f)
                curveTo(7.5837f, 12.9646f, 7.5f, 13.166f, 7.5f, 13.3759f)
                curveTo(7.5f, 13.5859f, 7.5837f, 13.7873f, 7.7328f, 13.9362f)
                curveTo(7.8075f, 14.0099f, 7.8963f, 14.0682f, 7.9938f, 14.1077f)
                curveTo(8.0913f, 14.1473f, 8.1957f, 14.1673f, 8.3011f, 14.1667f)
                curveTo(8.4064f, 14.1673f, 8.5108f, 14.1473f, 8.6084f, 14.1077f)
                curveTo(8.7059f, 14.0682f, 8.7946f, 14.0099f, 8.8694f, 13.9362f)
                lineTo(12.2632f, 10.5666f)
                curveTo(12.3383f, 10.4927f, 12.3978f, 10.4048f, 12.4384f, 10.308f)
                curveTo(12.4791f, 10.2111f, 12.5f, 10.1073f, 12.5f, 10.0023f)
                curveTo(12.5f, 9.8974f, 12.4791f, 9.7935f, 12.4384f, 9.6967f)
                curveTo(12.3978f, 9.5999f, 12.3383f, 9.512f, 12.2632f, 9.4381f)
                close()
            }
        }
            .build()
        return _rightArrow!!
    }

private var _rightArrow: ImageVector? = null
