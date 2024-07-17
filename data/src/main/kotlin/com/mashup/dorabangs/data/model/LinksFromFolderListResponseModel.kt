package com.mashup.dorabangs.data.model
import com.mashup.dorabangs.domain.model.LinkKeywordInfo
import com.mashup.dorabangs.domain.model.SavedLinkDetailInfo
import com.mashup.dorabangs.domain.model.SavedLinkListFromFolder
import kotlinx.serialization.Serializable

@Serializable
data class LinksFromFolderListResponseModel(
    val hasNext: Boolean,
    val list: List<SavedLinkInfo>,
    val total: Int
)

@Serializable
data class SavedLinkInfo(
    val createdAt: String,
    val description: String,
    val folderId: String,
    val id: String,
    val isFavorite: Boolean,
    val keywords: List<LinkKeyword>,
    val title: String,
    val url: String,
    val userId: String
)

@Serializable
data class LinkKeyword(
    val id: String,
    val name: String
)

fun LinksFromFolderListResponseModel.toDomain(): SavedLinkListFromFolder {
    return SavedLinkListFromFolder(
        hasNext = hasNext,
        total = total,
        list = list.map {it.toDomain() }
    )
}

fun SavedLinkInfo.toDomain(): SavedLinkDetailInfo {
    return SavedLinkDetailInfo(
        createdAt = createdAt,
        description = description,
        folderId = folderId,
        id = id,
        isFavorite = isFavorite,
        keywords = keywords.map { it.toDomain() },
        title = title,
        url = url,
        userId = userId
    )
}

fun LinkKeyword.toDomain(): LinkKeywordInfo {
    return LinkKeywordInfo(
        id = id,
        name = name
    )
}