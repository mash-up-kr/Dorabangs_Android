package com.mashup.dorabangs.data.model

import com.mashup.dorabangs.domain.model.NewFolderCreation
import kotlinx.serialization.Serializable

@Serializable
data class CreateFolderRequestModel(
    val names: List<String>,
)

fun NewFolderCreation.toData(): CreateFolderRequestModel = CreateFolderRequestModel(names = this.names)
