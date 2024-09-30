package com.mashup.dorabangs.data.model

import com.mashup.dorabangs.domain.model.AIClassificationFolder
import com.mashup.dorabangs.domain.model.AIClassificationFolders
import kotlinx.serialization.Serializable

@Serializable
data class AIClassificationFoldersResponseModel(
    val totalCounts: Int,
    val list: List<AIClassificationFolderResponseModel>,
)

@Serializable
data class AIClassificationFolderResponseModel(
    val folderName: String,
    val postCount: Int,
    val folderId: String,
    val isAIGenerated: Boolean,
)

fun AIClassificationFoldersResponseModel.toDomain() = AIClassificationFolders(
    totalCounts = this.totalCounts,
    list = this.list.map { it.toDomain() },
)

fun AIClassificationFolderResponseModel.toDomain() = AIClassificationFolder(
    folderId = this.folderId,
    folderName = this.folderName,
    postCount = this.postCount,
    isAIGenerated = this.isAIGenerated,
)
