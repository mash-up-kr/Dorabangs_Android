package com.mashup.dorabangs.data.model

import com.mashup.dorabangs.domain.model.CreateFolder

data class CreateFolderRequest(
    val names: List<String>,
)

fun CreateFolder.toData(): CreateFolderRequest = CreateFolderRequest(names = this.names)
