package com.mashup.dorabangs.core.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

object DoraRoundTokens {
    val Round99 = RoundedCornerShape(99.dp)
    val Round24 = RoundedCornerShape(24.dp)
    val Round16 = RoundedCornerShape(16.dp)
    val Round12 = RoundedCornerShape(12.dp)
    val TopRound12 = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
    val BottomRound12 = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
    val Round8 = RoundedCornerShape(8.dp)
    val Round4 = RoundedCornerShape(4.dp)
}

object BtnMaxRoundTokens {
    val FullButtonWidthRadius = DoraRoundTokens.Round99
    val MediumButtonWidthRadius = DoraRoundTokens.Round12
}

object DialogRoundTokens {
    val Radius = DoraRoundTokens.Round16
}
