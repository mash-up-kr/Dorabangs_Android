package com.dorabangs.feature.save

data class DoraSaveState(
    val title: String = "",
    val urlLink: String = "",
    val thumbnailUrl: String = "",
    val isShortLink: Boolean = false,
    val isError: Boolean = false,
)
