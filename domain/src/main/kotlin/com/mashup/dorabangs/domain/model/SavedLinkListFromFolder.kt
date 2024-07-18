package com.mashup.dorabangs.domain.model

data class SavedLinkDetailInfo(
    val createdAt: String,
    val description: String,
    val folderId: String,
    val id: String,
    val isFavorite: Boolean,
    val keywords: List<LinkKeywordInfo>,
    val title: String,
    val url: String,
    val userId: String,
)

data class LinkKeywordInfo(
    val id: String,
    val name: String,
)
