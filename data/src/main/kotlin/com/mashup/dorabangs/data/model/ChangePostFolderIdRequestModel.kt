package com.mashup.dorabangs.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChangePostFolderIdRequestModel(
    val folderId: String,
)
