package com.mashup.dorabangs.data.model
import com.mashup.dorabangs.domain.model.LinkKeywordInfo
import com.mashup.dorabangs.domain.model.PageData
import com.mashup.dorabangs.domain.model.PagingInfo
import com.mashup.dorabangs.domain.model.SavedLinkDetailInfo
import kotlinx.serialization.Serializable

@Serializable
data class LinksFromFolderResponseModel(
    val hasNext: Boolean,
    val list: List<SavedLinkInfo>,
    val total: Int,
)

@Serializable
data class SavedLinkInfo(
    val createdAt: String? = "",
    val description: String? = "",
    val folderId: String? = "",
    val id: String? = "",
    val isFavorite: Boolean = false,
    val keywords: List<LinkKeyword>? = listOf(),
    val title: String? = "",
    val url: String? = "",
    val userId: String? = "",
)

@Serializable
data class LinkKeyword(
    val id: String? = "",
    val name: String? = "",
)

fun LinksFromFolderResponseModel.toDomain(): PageData<List<SavedLinkDetailInfo>> {
    return PageData(
        data = list.map { it.toDomain() },
        pagingInfo = PagingInfo(
            total = total,
            hasNext = hasNext,
        ),
    )
}

fun SavedLinkInfo.toDomain(): SavedLinkDetailInfo {
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
    )
}

fun LinkKeyword.toDomain(): LinkKeywordInfo {
    return LinkKeywordInfo(
        id = id,
        name = name,
    )
}
