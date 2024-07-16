package com.mashup.dorabangs.data.model

import com.mashup.dorabangs.domain.model.EditFolder
import kotlinx.serialization.Serializable

@Serializable
data class EditFolderNameRequestModel(
    val name: String,
)

fun EditFolder.toData(): EditFolderNameRequestModel = EditFolderNameRequestModel(name = this.name)
