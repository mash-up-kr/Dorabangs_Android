package com.mashup.dorabangs.domain.model

data class AIClassificationFolders(
    val totalCounts: Int,
    val list: List<AIClassificationFolder>,
)

data class AIClassificationFolder(
    val folderName: String,
    val postCount: Int,
    val folderId: String,
)
