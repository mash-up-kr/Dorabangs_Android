package com.mashup.dorabangs.domain.model

data class EditCompleteFolderInfo(
    val completeFolderInfo: EditedFolderInfo = EditedFolderInfo(),
    val errorMsg: String = "",
)

data class EditedFolderInfo(
    val id: String = "",
    val name: String = "",
    val type: FolderType = FolderType.DEFAULT,
    val createAt: String = "",
)
