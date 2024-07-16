package com.mashup.dorabangs.domain.model

data class Posts(
    val metaData: PostsMetaData,
    val items: List<Post>,
)

data class PostsMetaData(
    val hasNext: Boolean = false,
    val total: Int = 0,
)

data class Post(
    val id: String = "",
    val folderId: String = "",
    val url: String = "",
    val title: String = "",
    val description: String = "",
    val isFavorite: Boolean = false,
    val readAt: String = "",
)