package com.mashup.dorabangs.feature.home

data class HomeState(
    val number: Int = 0,
    val copiedText: String = "",
    val shouldSnackBarShown: Boolean = false,
)

sealed class HomeSideEffect
