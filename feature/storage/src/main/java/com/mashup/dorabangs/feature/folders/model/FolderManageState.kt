package com.mashup.dorabangs.feature.folders.model

data class FolderManageState(
    val type: FolderManageType = FolderManageType.CREATE,
    val folderName: String = "",
    val helperEnable: Boolean = false,
    val helperMessage: String = "",
)
