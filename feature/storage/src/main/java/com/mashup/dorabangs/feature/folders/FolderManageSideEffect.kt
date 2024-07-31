package com.mashup.dorabangs.feature.folders

sealed class FolderManageSideEffect {
    object NavigateToBackStack : FolderManageSideEffect()
    object NavigateToComplete : FolderManageSideEffect()
}
