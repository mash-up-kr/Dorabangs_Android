package com.dorabangs.feature.save

sealed interface DoraSaveSideEffect {
    data class ClickItem(val index: Int) : DoraSaveSideEffect
    data class ClickSaveButton(val id: String) : DoraSaveSideEffect
}
