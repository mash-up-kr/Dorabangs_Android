package com.mashup.dorabangs.data.model

import com.mashup.dorabangs.domain.model.PostInfo
import kotlinx.serialization.Serializable

@Serializable
data class PostInfoRequestModel(
    val isFavorite: Boolean,
    val readAt: String,
)

fun PostInfoRequestModel.toDomain() = PostInfo(
    isFavorite = this.isFavorite,
    readAt = this.readAt,
)

fun PostInfo.toDomain() = PostInfoRequestModel(
    isFavorite = this.isFavorite,
    readAt = this.readAt,
)
