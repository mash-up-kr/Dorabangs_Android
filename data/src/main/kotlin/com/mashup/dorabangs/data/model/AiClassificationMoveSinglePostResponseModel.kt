package com.mashup.dorabangs.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AiClassificationMoveSinglePostResponseModel(
    @SerialName("list")
    val list: List<AiClassificationState?>?,
    @SerialName("metadata")
    val metadata: PagingMetaDataResponseModel?,
) {
    @Serializable
    data class AiClassificationState(
        @SerialName("aiStatus")
        val aiStatus: String?,
        @SerialName("createdAt")
        val createdAt: String?,
        @SerialName("description")
        val description: String?,
        @SerialName("folderId")
        val folderId: String?,
        @SerialName("keywords")
        val keywords: List<String?>?,
        @SerialName("postId")
        val postId: String?,
        @SerialName("readAt")
        val readAt: String?,
        @SerialName("thumbnailImgUrl")
        val thumbnailImgUrl: String?,
        @SerialName("title")
        val title: String?,
        @SerialName("url")
        val url: String?,
    )
}
