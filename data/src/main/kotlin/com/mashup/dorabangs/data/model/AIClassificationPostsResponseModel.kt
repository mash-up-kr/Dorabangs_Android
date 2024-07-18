package com.mashup.dorabangs.data.model

import com.mashup.dorabangs.domain.model.AIClassificationPosts
import kotlinx.serialization.Serializable

@Serializable
data class AIClassificationPostsResponseModel(
    val list: List<String>,
)

fun AIClassificationPostsResponseModel.toDomain() = AIClassificationPosts(
    list = this.list,
)
