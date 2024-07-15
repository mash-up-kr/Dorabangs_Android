package com.mashup.dorabangs.feature.createfolder


data class HomeCreateState(
    val temp: String = ""
)

sealed class HomeCreateSideEffect {

    data class ShowSnackBar(val copiedText: String) : HomeCreateSideEffect()
}