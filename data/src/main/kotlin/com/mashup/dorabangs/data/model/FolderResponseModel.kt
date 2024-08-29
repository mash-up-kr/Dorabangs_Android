package com.mashup.dorabangs.data.model

import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.domain.model.FolderList
import kotlinx.serialization.Serializable

@Serializable
data class FolderListResponseModel(
    val defaultFolders: List<FolderResponseModel> = emptyList(),
    val customFolders: List<FolderResponseModel> = emptyList(),
)

@Serializable
data class FolderResponseModel(
    val id: String? = null,
    val name: String = "",
    val type: String = "",
    val createdAt: String = "",
    val postCount: Int = 0,
)

fun FolderResponseModel.toDomain() = Folder(
    id = id.orEmpty(),
    name = name,
    type = type,
    createdAt = createdAt,
    postCount = postCount,
)

fun FolderListResponseModel.toDomain() = FolderList(
    defaultFolders = defaultFolders.map { it.toDomain() },
    customFolders = customFolders.map { it.toDomain() },
)
