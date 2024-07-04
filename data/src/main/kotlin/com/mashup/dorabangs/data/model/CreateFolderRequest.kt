package com.mashup.dorabangs.data.model

import com.mashup.dorabangs.domain.model.CreateFolder

data class CreateFolderRequest(
    val name: String,
)

fun CreateFolder.toData(): CreateFolderRequest = CreateFolderRequest(name = name)
