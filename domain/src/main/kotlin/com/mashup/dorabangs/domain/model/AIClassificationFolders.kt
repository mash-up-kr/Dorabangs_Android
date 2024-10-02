package com.mashup.dorabangs.domain.model

import androidx.annotation.DrawableRes

data class AIClassificationFolders(
    val totalCounts: Int,
    val list: List<AIClassificationFolder>,
)

data class AIClassificationFolder(
    val folderName: String,
    val postCount: Int,
    val folderId: String,
    val isAIGenerated: Boolean,
    @DrawableRes val icon: Int? = null,
)
