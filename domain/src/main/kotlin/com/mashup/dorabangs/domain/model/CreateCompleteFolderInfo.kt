package com.mashup.dorabangs.domain.model

data class CreateCompleteFolderInfo(
    val id: String = "",
    val name: String = "",
    val type: FolderType = FolderType.DEFAULT,
    val createAt: String = "",
)
