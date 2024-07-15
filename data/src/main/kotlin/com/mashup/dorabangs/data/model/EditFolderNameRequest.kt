package com.mashup.dorabangs.data.model

import com.mashup.dorabangs.domain.model.EditFolder
import kotlinx.serialization.Serializable

@Serializable
data class EditFolderNameRequest(
    val name: String,
)

fun EditFolder.toData(): EditFolderNameRequest = EditFolderNameRequest(name = this.name)
