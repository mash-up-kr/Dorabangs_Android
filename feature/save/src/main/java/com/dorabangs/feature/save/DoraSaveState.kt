package com.dorabangs.feature.save

data class DoraSaveState(
    val title: String = "",
    val urlLink: String = "",
    val thumbnailUrl: String = "",
    val isShortLink: Boolean = false,
    val isError: Boolean = false,
    val folderList: List<SelectableFolder> = listOf(),
)

data class SelectableFolder(
    val id: String?,
    val name: String,
    val type: String,
    val createdAt: String?,
    val postCount: Int?,
    val isSelected: Boolean,
)
