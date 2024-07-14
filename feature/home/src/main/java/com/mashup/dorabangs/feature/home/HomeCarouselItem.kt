package com.mashup.dorabangs.feature.home

import androidx.annotation.RawRes

data class HomeCarouselItem(
    @RawRes val lottieRes: Int,
    val description: String,
    val onClickButton: () -> Unit = {}
)
