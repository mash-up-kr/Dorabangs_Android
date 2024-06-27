package com.mashup.dorabangs.feature.home

sealed class HomeSideEffect {
    object ShowSnackBar : HomeSideEffect()
    object HideSnackBar : HomeSideEffect()
}
