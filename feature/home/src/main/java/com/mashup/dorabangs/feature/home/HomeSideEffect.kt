package com.mashup.dorabangs.feature.home

sealed class HomeSideEffect {
    data class ShowSnackBar(val copiedText: String) : HomeSideEffect()
    object HideSnackBar : HomeSideEffect()
    object NavigateToCreateFolder : HomeSideEffect()
    object NavigateToHome : HomeSideEffect()
}
