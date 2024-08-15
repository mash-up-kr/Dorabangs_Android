package com.mashup.dorabangs.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mashup.dorabangs.data.model.AIStatusResponseModel
import com.mashup.dorabangs.data.model.LinkKeywordResponseModel
import com.mashup.dorabangs.data.model.toDomain
import com.mashup.dorabangs.domain.model.AIStatus
import com.mashup.dorabangs.domain.model.LinkKeywordInfo
import com.mashup.dorabangs.domain.model.Post
import com.mashup.dorabangs.domain.model.SavedLinkDetailInfo

@Entity
data class LocalPostItemEntity(
    @PrimaryKey val id: String = "",
    val createdAt: String? = "",
    val description: String? = "",
    val folderId: String? = "",
    val isFavorite: Boolean = false,
    val keywords: List<LinkKeywordResponseModel>? = listOf(),
    val title: String? = "",
    val url: String? = "",
    val userId: String? = "",
    val thumbnailImgUrl: String? = "",
    val aiStatusResponseModel: AIStatusResponseModel = AIStatusResponseModel.NOTHING,
    val readAt: String?,
)

fun Post.toLocalEntity(): LocalPostItemEntity {
    return LocalPostItemEntity(
        id = id,
        createdAt = createdAt,
        description = description,
        folderId = folderId,
        isFavorite = isFavorite,
        keywords = keywords?.map { it.toData() },
        title = title,
        url = url,
        thumbnailImgUrl = thumbnailImgUrl,
        aiStatusResponseModel = aiStatus.toData(),
        readAt = readAt,
    )
}

fun SavedLinkDetailInfo.toLocalEntity(): LocalPostItemEntity {
    return LocalPostItemEntity(
        id = id.orEmpty(),
        createdAt = createdAt,
        description = description,
        folderId = folderId,
        isFavorite = isFavorite ?: false,
        keywords = keywords?.map { it.toData() },
        title = title,
        url = url,
        thumbnailImgUrl = thumbnailImgUrl,
        aiStatusResponseModel = aiStatus.toData(),
        readAt = readAt,
    )
}

fun AIStatus.toData() = when (this) {
    AIStatus.IN_PROGRESS -> AIStatusResponseModel.IN_PROGRESS
    AIStatus.SUCCESS -> AIStatusResponseModel.SUCCESS
    AIStatus.NOTHING -> AIStatusResponseModel.NOTHING
}
fun LinkKeywordInfo.toData(): LinkKeywordResponseModel {
    return LinkKeywordResponseModel(
        id = id,
        name = name.orEmpty(),
    )
}

fun LocalPostItemEntity.toPost(): Post {
    return Post(
        id = id,
        createdAt = createdAt.orEmpty(),
        description = description.orEmpty(),
        folderId = folderId.orEmpty(),
        isFavorite = isFavorite,
        keywords = keywords?.map { it.toDomain() },
        title = title.orEmpty(),
        url = url.orEmpty(),
        thumbnailImgUrl = thumbnailImgUrl.orEmpty(),
        aiStatus = aiStatusResponseModel.toDomain(),
        readAt = readAt,
    )
}

fun LocalPostItemEntity.toSavedLinkDetailInfo(): SavedLinkDetailInfo {
    return SavedLinkDetailInfo(
        id = id,
        createdAt = createdAt.orEmpty(),
        description = description.orEmpty(),
        folderId = folderId.orEmpty(),
        isFavorite = isFavorite,
        keywords = keywords?.map { it.toDomain() },
        title = title.orEmpty(),
        url = url.orEmpty(),
        thumbnailImgUrl = thumbnailImgUrl.orEmpty(),
        aiStatus = aiStatusResponseModel.toDomain(),
        readAt = readAt,
        userId = userId,
    )
}
