package com.mashup.dorabangs.core.designsystem.component.util

import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieLoader(
    @RawRes lottieRes: Int,
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieRes))
    LottieAnimation(
        modifier = modifier,
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )
}
