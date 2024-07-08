package com.mashup.dorabangs.feature.createfolder

data class HomeCreateFolderState(
    val folderName: String = "",
    val helperEnable: Boolean = false,
    val helperMessage: String = "",
)
