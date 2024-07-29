package com.mashup.dorabangs.data.model

import com.mashup.dorabangs.domain.model.AIStatus
import com.mashup.dorabangs.domain.model.PageData
import com.mashup.dorabangs.domain.model.PagingInfo
import com.mashup.dorabangs.domain.model.Post
import com.mashup.dorabangs.domain.model.Posts
import com.mashup.dorabangs.domain.model.PostsMetaData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostsResponseModel(
    val metadata: PagingMetaDataResponseModel,
    val list: List<PostResponseModel> = emptyList(),
)

@Serializable
data class PostResponseModel(
    val id: String = "",
    val folderId: String = "",
    val url: String = "",
    val title: String = "",
    val description: String = "",
    val isFavorite: Boolean = false,
    val createdAt: String = "",
    val aiStatus: AIStatusResponseModel = AIStatusResponseModel.NOTHING,
    val readAt: String = "",
)

@Serializable
enum class AIStatusResponseModel {
    @SerialName("in_progress")
    IN_PROGRESS,

    @SerialName("success")
    SUCCESS,

    NOTHING,
}

fun PostResponseModel.toDomain() = Post(
    id = id,
    folderId = folderId,
    url = url,
    title = title,
    description = description,
    isFavorite = isFavorite,
    createdAt = createdAt,
    aiStatus = aiStatus.toDomain(),
)

fun PagingMetaDataResponseModel.toDomain() = PostsMetaData(
    hasNext = hasNext,
    total = total,
)

fun PostsResponseModel.toDomain() = Posts(
    metaData = metadata.toDomain(),
    items = list.map { it.toDomain() },
)

fun PostsResponseModel.toPagingDomain(): PageData<List<Post>> {
    return PageData(
        data = list.map { it.toDomain() },
        pagingInfo = PagingInfo(
            total = metadata.total,
            hasNext = metadata.hasNext,
        ),
    )
}

fun AIStatusResponseModel.toDomain() = when(this) {
    AIStatusResponseModel.IN_PROGRESS -> AIStatus.IN_PROGRESS
    AIStatusResponseModel.SUCCESS -> AIStatus.SUCCESS
    AIStatusResponseModel.NOTHING -> AIStatus.NOTHING
}
