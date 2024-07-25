package com.mashup.dorabangs.feature.folders

sealed class FolderManageSideEffect {
    data class NavigateToBackStack(val folderName: String) : FolderManageSideEffect()
}
