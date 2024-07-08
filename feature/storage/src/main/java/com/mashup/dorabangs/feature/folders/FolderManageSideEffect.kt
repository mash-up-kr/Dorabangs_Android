package com.mashup.dorabangs.feature.folders

sealed class FolderManageSideEffect {
    object NavigateToStorage : FolderManageSideEffect()
}
