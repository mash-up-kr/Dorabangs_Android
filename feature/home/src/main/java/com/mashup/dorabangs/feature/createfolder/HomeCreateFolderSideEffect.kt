package com.mashup.dorabangs.feature.createfolder

sealed class HomeCreateFolderSideEffect {
    object NavigateToHome: HomeCreateFolderSideEffect()
}