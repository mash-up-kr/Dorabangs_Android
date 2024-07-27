package com.mashup.feature.classification

import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChipUiModel

data class ClassificationState(
    val chipState: ChipState = ChipState(),
    val selectedFolder: String = "디자인", // TODO- 수정 예정
    val cardInfoList: List<FeedCardUiModel> = FeedCardUiModel.getDefaultFeedCard(),
    val isClassificationComplete: Boolean = false,
)

data class ChipState(
    val totalCount: Int = 0,
    val chipList: List<DoraChipUiModel> = listOf(),
)
