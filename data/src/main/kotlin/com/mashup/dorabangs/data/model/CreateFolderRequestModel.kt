package com.mashup.dorabangs.data.model

import com.mashup.dorabangs.domain.model.NewFolderNameList
import kotlinx.serialization.Serializable

@Serializable
data class CreateFolderRequestModel(
    val names: List<String>,
)

fun NewFolderNameList.toData(): CreateFolderRequestModel = CreateFolderRequestModel(names = this.names)
