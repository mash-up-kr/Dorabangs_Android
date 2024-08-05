package com.mashup.feature.classification

import com.mashup.dorabangs.core.designsystem.component.chips.FeedUiModel

data class ClassificationState(
    val chipState: ChipState = ChipState(),
    val selectedFolder: String = "디자인",
)

data class ChipState(
    val totalCount: Int = 0,
    val currentIndex: Int = 0,
    val chipList: List<FeedUiModel.DoraChipUiModel> = listOf(),
)
