package com.mashup.dorabangs.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mashup.dorabangs.core.designsystem.R as coreR
import com.mashup.dorabangs.core.designsystem.component.util.LottieLoader

@Composable
fun SplashScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        LottieLoader(
            lottieRes = coreR.raw.splash,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
