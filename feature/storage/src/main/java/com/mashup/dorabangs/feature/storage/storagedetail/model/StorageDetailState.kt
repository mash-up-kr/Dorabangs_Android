package com.mashup.dorabangs.feature.storage.storagedetail.model

import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel

data class StorageDetailState(
    val title: String = "나중에 읽을 링크",
    val postCount: Int = 0,
    val tabTitleList: List<StorageDetailTab> = getDefaultTabTitleList(),
    val selectedIdx: Int = 0,
    val isLatestSort: Boolean = true,
    val postList: List<FeedCardUiModel> = listOf(),
) {
    companion object {
        fun getDefaultTabTitleList() = listOf(StorageDetailTab.ALL, StorageDetailTab.UNREAD)
    }
}
