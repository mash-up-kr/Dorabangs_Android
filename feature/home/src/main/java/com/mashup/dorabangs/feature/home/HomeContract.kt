package com.mashup.dorabangs.feature.home

import com.mashup.dorabangs.core.designsystem.component.chips.DoraChipUiModel

data class HomeState(
    val tapElements: List<DoraChipUiModel> = emptyList(),
)

sealed class HomeSideEffect
