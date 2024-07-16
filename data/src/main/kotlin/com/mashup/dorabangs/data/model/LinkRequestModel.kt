package com.mashup.dorabangs.data.model

import com.mashup.dorabangs.domain.model.Link
import kotlinx.serialization.Serializable

@Serializable
data class LinkRequestModel(
    val folderId: String,
    val url: String,
)

fun LinkRequestModel.toDomain() = Link(
    folderId = this.folderId,
    url = this.url,
)

fun Link.toData() = LinkRequestModel(
    folderId = this.folderId,
    url = this.url,
)
