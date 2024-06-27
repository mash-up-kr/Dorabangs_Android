package com.mashup.dorabangs.feature.home

data class HomeState(
    val number: Int = 0,
    val clipBoardState: ClipBoardState = ClipBoardState(),
)

data class ClipBoardState(
    val copiedText: String = "",
    val shouldSnackBarShown: Boolean = false,
)
