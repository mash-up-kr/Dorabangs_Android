package com.mashup.dorabangs.core.designsystem.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object DoraColorTokens {
    val P1 = Color(0xFFFFFFFF)
    val G9 = Color(0xFF121212)
    val G8 = Color(0xFF1F1F20)
    val G7 = Color(0xFF353638)
    val G6 = Color(0xFF4B4C4E)
    val G5 = Color(0xFF6E7277)
    val G4 = Color(0xFFADB5BD)
    val G3 = Color(0xFFCDD1D9)
    val G2 = Color(0xFFF0F3F7)
    val G1 = Color(0xFFF4F6F8)
    val White = Color(0xFFFFFFFF)
    val Black = Color(0xFF000000)
    val Alert = Color(0xFFFF5D47)
    val Dimend = Color(0xB2121212)
    val Primary = Color(0xFF7764FF)
}

object DoraGradientToken {
    val Gradient5 = Brush.linearGradient(
        listOf(
            Color(0xFF7552FF),
            Color(0xFF788EFF),
        )
    )
    val Gradient4 = Brush.linearGradient(
        listOf(
            Color(0xFF7764FF),
            Color(0xFFE7E7FF),
            Color(0xFFFFE7F3),
        )
    )
    val Gradient3 = Brush.linearGradient(
        listOf(
            Color(0xFFE2ECFF),
            Color(0xFFF7F7FF),
            Color(0xFFFFEAF4),
        )
    )
    val Gradient2 = Brush.linearGradient(
        listOf(
            Color(0xFFF3FBFF),
            Color(0xFFF5F5FF),
            Color(0xFFF3FBFF),
        )
    )
    val Gradient1 = Brush.linearGradient(
        listOf(
            Color(0xFFFFF9FB),
            Color(0xFFF9F9FF),
            Color(0xFFF9F8FF),
        )
    )
}

object BtnMaxColorTokens {
    val ContainerColor1 = DoraColorTokens.G9
    val ContentColor1 = DoraColorTokens.G1
    val ContainerColor1_Off = DoraColorTokens.G2
    val ContentColor_1Off = DoraColorTokens.G4
    val ContainerColor2 = DoraColorTokens.G1
    val ContentColor2 = DoraColorTokens.G9
}

object DialogColorTokens {
    val BackgroundColor = DoraColorTokens.P1
    val TitleColor = DoraColorTokens.G8
    val ContentColor = DoraColorTokens.G5
}

object ClipBoardColorTokens {
    val ContainerColor1 = DoraColorTokens.G9
    val UrlLinkMainColor1 = DoraColorTokens.White
    val UrlLinkSubColor1 = DoraColorTokens.G3
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
    val TitleTextColor = DoraColorTokens.G9
    val LinkTextColor = DoraColorTokens.G4
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
    val ContainerColor = if (isSelected) DoraColorTokens.Black else DoraColorTokens.White
    val OnContainerColor = if (isSelected) DoraColorTokens.White else DoraColorTokens.G6
    val BorderColor = if (isSelected) DoraColorTokens.Black else DoraColorTokens.G2
}

object BottomSheetColorTokens {
    val MoreViewBackgroundColor = DoraColorTokens.G1
    val MovingFolderColor = DoraColorTokens.White
    val HandleColor = DoraColorTokens.G2
    val ItemColor = DoraColorTokens.White
    val DividerColor = DoraColorTokens.G3
}
