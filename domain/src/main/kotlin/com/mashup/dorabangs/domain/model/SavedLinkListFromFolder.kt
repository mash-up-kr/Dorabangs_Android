package com.mashup.dorabangs.domain.model

data class SavedLinkDetailInfo(
    val id: String?,
    val folderId: String?,
    val url: String?,
    val title: String?,
    val description: String?,
    val isFavorite: Boolean?,
    val userId: String?,
    val createdAt: String?,
    val keywords: List<LinkKeywordInfo>?,
)

data class LinkKeywordInfo(
    val id: String?,
    val name: String?,
)
