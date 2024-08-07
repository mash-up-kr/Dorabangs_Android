package com.mashup.dorabangs.data.model
import com.mashup.dorabangs.domain.model.LinkKeywordInfo
import com.mashup.dorabangs.domain.model.PageData
import com.mashup.dorabangs.domain.model.PagingInfo
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
    val aiStatusResponseModel: AIStatusResponseModel = AIStatusResponseModel.NOTHING,
)

@Serializable
data class LinkKeywordResponseModel(
    val id: String = "",
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
        aiStatus = aiStatusResponseModel.toDomain(),
    )
}

fun LinkKeywordResponseModel.toDomain(): LinkKeywordInfo {
    return LinkKeywordInfo(
        id = id,
        name = name,
    )
}
