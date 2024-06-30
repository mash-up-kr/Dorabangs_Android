package com.mashup.dorabangs.feature.home

import clipboard.isValidUrl
import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChipUiModel

data class HomeState(
    val clipBoardState: ClipBoardState = ClipBoardState(),
    val tapElements: List<DoraChipUiModel> = emptyList(),
    val feedCards: List<FeedCardUiModel> = emptyList(),
    val selectedIndex: Int = 0,
    val isShowMoreButtonSheet: Boolean = true,
    val isShowDialog: Boolean = false,
    val isShowMovingFolderSheet: Boolean = false,
)

data class ClipBoardState(
    val copiedText: String = "",
) {
    val isValidUrl = copiedText.isNotBlank() && copiedText.isValidUrl()
}
