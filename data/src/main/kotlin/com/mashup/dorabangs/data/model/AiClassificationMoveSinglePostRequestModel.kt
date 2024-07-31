package com.mashup.dorabangs.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AiClassificationMoveSinglePostRequestModel(
    val suggestionFolderId: String,
)
