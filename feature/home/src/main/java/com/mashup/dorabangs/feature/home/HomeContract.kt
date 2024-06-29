package com.mashup.dorabangs.feature.home

import clipboard.isValidUrl

data class HomeState(
    val number: Int = 0,
    val clipBoardState: ClipBoardState = ClipBoardState(),
)

data class ClipBoardState(
    val copiedText: String = "",
) {
    val isValidUrl = copiedText.isNotBlank() && copiedText.isValidUrl()
}
