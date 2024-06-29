package com.mashup.dorabangs.feature.home

import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChipUiModel

data class HomeState(
    val tapElements: List<DoraChipUiModel> = emptyList(),
    val feedCards: List<FeedCardUiModel> = emptyList(),
    val selectedIndex: Int = 0,
)

sealed class HomeSideEffect
