package com.mashup.dorabangs.data.datasource.save

data class DoraUrlCheckResponse(
    val urlLink: String = "",
    val title: String = "",
    val thumbnailUrl: String = "",
    val isShortLink: Boolean = false,
)
