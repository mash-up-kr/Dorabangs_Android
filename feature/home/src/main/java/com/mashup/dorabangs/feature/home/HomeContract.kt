package com.mashup.dorabangs.feature.home

data class HomeState(
    val number: Int = 0,
)

sealed class HomeSideEffect
