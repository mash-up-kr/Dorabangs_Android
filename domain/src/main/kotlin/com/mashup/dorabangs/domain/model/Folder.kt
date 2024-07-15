package com.mashup.dorabangs.domain.model

data class FolderList(
    val defaultFolders: List<Folder>,
    val customFolders: List<Folder>,
) {
    fun toList() = defaultFolders + customFolders
}

data class Folder(
    val id: String?,
    val name: String,
    val type: String,
    val createdAt: String?,
    val postCount: Int?,
)
