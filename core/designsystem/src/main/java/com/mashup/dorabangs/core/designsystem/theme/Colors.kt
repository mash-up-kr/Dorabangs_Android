package com.mashup.dorabangs.core.designsystem.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object DoraColorTokens {
    val P1 = Color(0xFFFFFFFF)
    val G9 = Color(0xFF27292C)
    val G8 = Color(0xFF2B2D34)
    val G7 = Color(0xFF404449)
    val G6 = Color(0xFF707276)
    val G5 = Color(0xFF878E99)
    val G4 = Color(0xFFC0C5CC)
    val G3 = Color(0xFFE6E7EB)
    val G2 = Color(0xFFF5F5F6)
    val G1 = Color(0xFFFAFAFB)
    val White = Color(0xFFFFFFFF)
    val Black = Color(0xFF000000)
    val SurfaceBlack = Color(0xFF27292C)
    val Alert = Color(0xFFFF5D47)
    val Dimend = Color(0x1212124D)
    val Primary500 = Color(0xFF666FFF)
    val Primary100 = Color(0xFFF6F6FF)
}

object DoraGradientToken {
    val Gradient5 = Brush.linearGradient(
        listOf(
            Color(0xFF7552FF),
            Color(0xFF788EFF),
        ),
    )
    val Gradient4 = Brush.linearGradient(
        listOf(
            Color(0xFF7764FF),
            Color(0xFFE7E7FF),
            Color(0xFFFFE7F3),
        ),
    )
    val Gradient3 = Brush.linearGradient(
        listOf(
            Color(0xFFEAF2FF),
            Color(0xFFEDF1FF),
            Color(0xFFEEEFFF),
            Color(0xFFECEFFF),
        ),
    )
    val Gradient2 = Brush.linearGradient(
        listOf(
            Color(0xFFF6FBFF),
            Color(0xFFF9FBFF),
            Color(0xFFFBF9FF),
            Color(0xFFF9F9FF),
        ),
    )
    val Gradient1 = Brush.linearGradient(
        listOf(
            Color(0xFFFFF9FB),
            Color(0xFFF9F9FF),
            Color(0xFFF9F8FF),
        ),
    )
}

object BtnMaxColorTokens {
    val ContainerColor1 = DoraColorTokens.G9
    val ContentColor1 = DoraColorTokens.G1
    val ContainerColor1_Off = DoraColorTokens.G2
    val ContentColor_1Off = DoraColorTokens.G4
    val ContainerColor2 = DoraColorTokens.G1
    val ContentColor2 = DoraColorTokens.G9
    val Transparent = Color.Transparent
    val ContainerColor3 = DoraColorTokens.G8
}

object DialogColorTokens {
    val BackgroundColor = DoraColorTokens.P1
    val TitleColor = DoraColorTokens.G8
    val ContentColor = DoraColorTokens.G5
}

object ClipBoardColorTokens {
    val ContainerColor1 = DoraColorTokens.SurfaceBlack
    val UrlLinkMainColor1 = DoraColorTokens.G4
    val UrlLinkSubColor1 = DoraColorTokens.G1
    val ArrowColor = DoraColorTokens.G3
}

object TextFieldColorTokens {
    val BackGroundColor = DoraColorTokens.G1
    val HintTextColor = DoraColorTokens.G4
    val TextCounterColor = DoraColorTokens.G6
    val HelperRoundedColor = DoraColorTokens.Alert
}

object TextFieldLabelColorTokens {
    val LabelColor = DoraColorTokens.G9
}

object TextFieldHelperTextColorTokens {
    val LabelColor = DoraColorTokens.Alert
}

object LinkSaveColorTokens {
    val ContainerColor = DoraColorTokens.White
    val TitleTextColor = DoraColorTokens.G8
    val LinkTextColor = DoraColorTokens.G5
    val LinkContainerBackgroundColor = DoraColorTokens.G1
}

object TopBarColorTokens {
    val ContainerColor = DoraColorTokens.White
    val OnContainerColor = DoraColorTokens.G9
    val OnContainerColorHome = DoraColorTokens.G5
}

object ChipsColorTokens {
    val Containercolor = DoraColorTokens.White
}

class ChipColorTokens(isSelected: Boolean) {
    val ContainerColor = if (isSelected) DoraColorTokens.G8 else DoraColorTokens.G1
    val OnContainerColor = if (isSelected) DoraColorTokens.G1 else DoraColorTokens.G7
    val OnContainerColor2 = if (isSelected) DoraColorTokens.White else DoraColorTokens.G6
    val BorderColor = if (isSelected) DoraColorTokens.G8 else DoraColorTokens.G2
}

object BottomSheetColorTokens {
    val MoreViewBackgroundColor = DoraColorTokens.G2
    val MovingFolderColor = DoraColorTokens.White
    val LightHandleColor = DoraColorTokens.G2
    val DarkHandleColor = DoraColorTokens.G3
    val ItemColor = DoraColorTokens.White
    val DividerColor = DoraColorTokens.G3
}
