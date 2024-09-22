package com.mashup.dorabangs.feature.home

sealed class HomeSideEffect {
    data class ShowCopiedUrlSnackBar(val copiedText: String) : HomeSideEffect()
    object HideSnackBar : HomeSideEffect()
    object NavigateToCreateFolder : HomeSideEffect()
    object NavigateToHome : HomeSideEffect()
    data class SaveLink(val folderId: String, val urlLink: String) : HomeSideEffect()
    object NavigateHomeAfterSaveLink : HomeSideEffect()
    data class NavigateSelectLinkFromService(val urlLink: String) : HomeSideEffect()
    object NavigateToCompleteMovingFolder : HomeSideEffect()
    data class ShowToastSnackBar(val toastMsg: String) : HomeSideEffect()
}
