package com.mashup.dorabangs.feature.storage.storage.model

import com.mashup.dorabangs.domain.model.Folder

data class StorageListState(
    val defaultStorageFolderList: List<Folder> = listOf(),
    val customStorageFolderList: List<Folder> = listOf(),
    val isShowMoreButtonSheet: Boolean = false,
    val isShowDialog: Boolean = false,
)
