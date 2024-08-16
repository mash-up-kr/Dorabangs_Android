package com.mashup.dorabangs.feature.storage.storage.model

import com.mashup.dorabangs.core.designsystem.component.toast.ToastStyle
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.feature.storage.storagedetail.model.ToastState

data class StorageListState(
    val defaultStorageFolderList: List<Folder> = listOf(),
    val customStorageFolderList: List<Folder> = listOf(),
    val selectedFolderId: String = "",
    val isShowMoreButtonSheet: Boolean = false,
    val isShowDialog: Boolean = false,
    val needToUpdate: Boolean = false,
    val isFirstEntry: Boolean = true,
    val toastState: ToastState = ToastState(),
)

data class ToastState(
    val text: String = "",
    val toastStyle: ToastStyle = ToastStyle.CHECK,
)
