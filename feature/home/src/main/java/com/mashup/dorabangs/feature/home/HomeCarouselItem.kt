package com.mashup.dorabangs.feature.home

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.compose.ui.text.AnnotatedString

data class HomeCarouselItem(
    @RawRes val lottieRes: Int,
    @DrawableRes val indicatorIcon: Int? = null,
    val description: AnnotatedString,
    val onClickButton: () -> Unit = {},
    val isVisible: Boolean = true,
)
