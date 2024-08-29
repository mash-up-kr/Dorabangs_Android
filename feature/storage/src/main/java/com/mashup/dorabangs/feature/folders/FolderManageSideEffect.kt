package com.mashup.dorabangs.feature.folders

sealed class FolderManageSideEffect {
    object NavigateToBackStack : FolderManageSideEffect()
    data class NavigateToComplete(val folderName: String) : FolderManageSideEffect()
}
