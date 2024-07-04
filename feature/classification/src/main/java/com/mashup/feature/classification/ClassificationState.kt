package com.mashup.feature.classification

import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel

data class ClassificationState(
    val folderList: List<String> = listOf(),
    val selectedFolder: String = "디자인",
    val cardInfoList: List<FeedCardUiModel> = FeedCardUiModel.getDefaultFeedCard(),
)
