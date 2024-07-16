package com.mashup.dorabangs.data.model

import com.mashup.dorabangs.domain.model.FolderRename
import kotlinx.serialization.Serializable

@Serializable
data class EditFolderNameRequestModel(
    val name: String,
)

fun FolderRename.toData(): EditFolderNameRequestModel = EditFolderNameRequestModel(name = this.name)
