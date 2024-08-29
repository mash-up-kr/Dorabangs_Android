package com.mashup.dorabangs.feature.storage.storagedetail.model

sealed class StorageDetailSideEffect {
    object NavigateToHome : StorageDetailSideEffect()
    data class NavigateToFolderManage(val itemId: String) : StorageDetailSideEffect()
    object RefreshPagingList : StorageDetailSideEffect()
    object ShowToastSnackBar : StorageDetailSideEffect()
}
