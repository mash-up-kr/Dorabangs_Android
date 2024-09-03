package com.mashup.dorabangs.feature.home

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes

data class HomeCarouselItem(
    @RawRes val lottieRes: Int,
    @DrawableRes val indicatorIcon: Int? = null,
    val description: String,
    val onClickButton: () -> Unit = {},
    val isVisible: Boolean = true,
)
