package com.mashup.dorabangs.feature.home

import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChipUiModel
import clipboard.isValidUrl

data class HomeState(
    val clipBoardState: ClipBoardState = ClipBoardState(),
    val tapElements: List<DoraChipUiModel> = emptyList(),
    val feedCards: List<FeedCardUiModel> = emptyList(),
    val selectedIndex: Int = 0,
)

data class ClipBoardState(
    val copiedText: String = "",
) {
    val isValidUrl = copiedText.isNotBlank() && copiedText.isValidUrl()
}
