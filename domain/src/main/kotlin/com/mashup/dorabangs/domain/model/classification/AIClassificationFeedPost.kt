package com.mashup.dorabangs.domain.model.classification

data class AIClassificationFeedPost(
    val postId: String,
    val folderId: String,
    val title: String?,
    val content: String?,
    val createdAt: String?,
    val keywordList: List<String>,
    val thumbnail: String?,
    val isFavorite: Boolean = false,
    val isLoading: Boolean = false,
    val aiStatus: String = "",
    val readAt: String = "",
    val url: String = "",
)
