package com.mashup.dorabangs.core.designsystem.component.card

data class FeedCardUiModel(
    val title: String,
    val content: String,
    val category: String,
    val createdAt: Int,
    val keywordList: List<String>,
    val thumbnail: Int, // TODO - url String 변경 필요

)
