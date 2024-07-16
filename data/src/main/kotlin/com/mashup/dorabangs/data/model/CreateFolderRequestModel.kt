package com.mashup.dorabangs.data.model

import com.mashup.dorabangs.domain.model.CreateFolder
import kotlinx.serialization.Serializable

@Serializable
data class CreateFolderRequestModel(
    val names: List<String>,
)

fun CreateFolder.toData(): CreateFolderRequestModel = CreateFolderRequestModel(names = this.names)
