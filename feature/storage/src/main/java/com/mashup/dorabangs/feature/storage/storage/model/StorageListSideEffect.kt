package com.mashup.dorabangs.feature.storage.storage.model

sealed class StorageListSideEffect {
    object NavigateToFolderManage : StorageListSideEffect()
    object ShowFolderRemoveToastSnackBar : StorageListSideEffect()
}
