package com.mashup.dorabangs.domain.model

import java.io.Serializable

data class FolderList(
    val defaultFolders: List<Folder>,
    val customFolders: List<Folder>,
)

@kotlinx.serialization.Serializable
data class Folder(
    val id: String? = "",
    val name: String = "",
    val type: String = "",
    val createdAt: String = "",
    val postCount: Int = 0,
) : Serializable
