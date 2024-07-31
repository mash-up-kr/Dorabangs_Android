package com.mashup.feature.classification

import androidx.paging.PagingData
import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChipUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ClassificationState(
    val chipState: ChipState = ChipState(),
    val selectedFolder: String = "디자인", // TODO- 수정 예정
    val cardInfoList: StateFlow<PagingData<FeedCardUiModel>> = MutableStateFlow(PagingData.empty()),
    val isClassificationComplete: Boolean = false,
)

data class ChipState(
    val totalCount: Int = 0,
    val currentIndex: Int = 0,
    val chipList: List<DoraChipUiModel> = listOf(),
)
