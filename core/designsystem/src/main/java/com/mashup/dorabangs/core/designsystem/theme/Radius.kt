package com.mashup.dorabangs.core.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

object DoraRoundTokens {
    val Round99 = RoundedCornerShape(99.dp)
    val Round50 = RoundedCornerShape(50.dp)
    val Round24 = RoundedCornerShape(24.dp)
    val Round20 = RoundedCornerShape(20.dp)
    val Round16 = RoundedCornerShape(16.dp)
    val Round12 = RoundedCornerShape(12.dp)
    val TopRound12 = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
    val TopRound16 = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    val BottomRound12 = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
    val Round8 = RoundedCornerShape(8.dp)
    val Round4 = RoundedCornerShape(4.dp)
}

object BtnMaxRoundTokens {
    val FullButtonWidthRadius = DoraRoundTokens.Round99
    val SmallIconButtonRadius = DoraRoundTokens.Round50
    val MediumButtonWidthRadius = DoraRoundTokens.Round12
    val SmallButtonWidthRadius = DoraRoundTokens.Round8
}

object DialogRoundTokens {
    val Radius = DoraRoundTokens.Round16
}
