package com.mashup.dorabangs.data.model

import com.mashup.dorabangs.domain.model.NewFolderName
import kotlinx.serialization.Serializable

@Serializable
data class EditFolderNameRequestModel(
    val name: String,
)

fun NewFolderName.toData(): EditFolderNameRequestModel = EditFolderNameRequestModel(name = this.name)
