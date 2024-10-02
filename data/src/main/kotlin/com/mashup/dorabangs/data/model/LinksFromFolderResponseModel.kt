package com.mashup.dorabangs.data.model

import com.mashup.dorabangs.domain.model.LinkKeywordInfo
import com.mashup.dorabangs.domain.model.PageData
import com.mashup.dorabangs.domain.model.PagingInfo
import com.mashup.dorabangs.domain.model.Post
import com.mashup.dorabangs.domain.model.Posts
import com.mashup.dorabangs.domain.model.SavedLinkDetailInfo
import kotlinx.serialization.Serializable

@Serializable
data class LinksFromFolderResponseModel(
    val metadata: PagingMetaDataResponseModel,
    val list: List<SavedLinkInfoResponseModel>,
)

@Serializable
data class SavedLinkInfoResponseModel(
    val createdAt: String? = "",
    val description: String? = "",
    val folderId: String? = "",
    val id: String? = "",
    val isFavorite: Boolean = false,
    val keywords: List<LinkKeywordResponseModel>? = listOf(),
    val title: String? = "",
    val url: String? = "",
    val userId: String? = "",
    val thumbnailImgUrl: String = "",
    val aiStatus: AIStatusResponseModel = AIStatusResponseModel.NOTHING,
    val readAt: String?,
)

@Serializable
data class LinkKeywordResponseModel(
    val id: String? = "",
    val name: String = "",
)

fun LinksFromFolderResponseModel.toDomain(): PageData<List<SavedLinkDetailInfo>> {
    return PageData(
        data = list.map { it.toDomain() },
        pagingInfo = PagingInfo(
            total = metadata.total,
            hasNext = metadata.hasNext,
        ),
    )
}

fun LinksFromFolderResponseModel.toDomainModel() =
    Posts(
        metaData = metadata.toDomain(),
        items = list.map {
            Post(
                id = it.id.orEmpty(),
                folderId = it.folderId.orEmpty(),
                url = it.url.orEmpty(),
                title = it.title.orEmpty(),
                description = it.description.orEmpty(),
                isFavorite = it.isFavorite,
                createdAt = it.createdAt.orEmpty(),
                thumbnailImgUrl = it.thumbnailImgUrl,
                aiStatus = it.aiStatus.toDomain(),
                readAt = it.readAt,
                keywords = it.keywords?.map { keyword -> keyword.toDomain() },
            )
        },
    )

fun SavedLinkInfoResponseModel.toDomain(): SavedLinkDetailInfo {
    return SavedLinkDetailInfo(
        createdAt = createdAt,
        description = description,
        folderId = folderId,
        id = id,
        isFavorite = isFavorite,
        keywords = keywords?.map { it.toDomain() },
        title = title,
        url = url,
        userId = userId,
        thumbnailImgUrl = thumbnailImgUrl,
        aiStatus = aiStatus.toDomain(),
        readAt = readAt,
    )
}

fun LinkKeywordResponseModel.toDomain(): LinkKeywordInfo {
    return LinkKeywordInfo(
        id = id.orEmpty(),
        name = name,
    )
}
