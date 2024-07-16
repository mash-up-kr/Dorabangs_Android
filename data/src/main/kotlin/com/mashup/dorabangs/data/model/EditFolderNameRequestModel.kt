package com.mashup.dorabangs.data.model

import com.mashup.dorabangs.domain.model.FolderEdition
import kotlinx.serialization.Serializable

@Serializable
data class EditFolderNameRequestModel(
    val name: String,
)

fun FolderEdition.toData(): EditFolderNameRequestModel = EditFolderNameRequestModel(name = this.name)
