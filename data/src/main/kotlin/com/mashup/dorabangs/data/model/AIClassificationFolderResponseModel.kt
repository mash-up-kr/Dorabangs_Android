package com.mashup.dorabangs.data.model

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
)
