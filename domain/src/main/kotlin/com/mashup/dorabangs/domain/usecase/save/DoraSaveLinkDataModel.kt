package com.mashup.dorabangs.domain.usecase.save

data class DoraSaveLinkDataModel(
    val urlLink: String = "",
    val title: String = "",
    val thumbnailUrl: String = "",
    val isShortLink: Boolean = false,
)
