package com.mashup.dorabangs.feature.storage.storagedetail.model

import androidx.paging.PagingData
import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel
import com.mashup.dorabangs.domain.model.SavedLinkDetailInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class StorageDetailState(
    val title: String = "나중에 읽을 링크",
    val postCount: Int = 0,
    val tabTitleList: List<StorageDetailTab> = getDefaultTabTitleList(),
    val selectedIdx: Int = 0,
    val isLatestSort: Boolean = false,
    val postList: List<FeedCardUiModel> = listOf(),
    val pagingList: Flow<PagingData<FeedCardUiModel>> = emptyFlow(),
) {
    companion object {
        fun getDefaultTabTitleList() = listOf(StorageDetailTab.ALL, StorageDetailTab.UNREAD)
    }
}

fun SavedLinkDetailInfo.toUiModel(): FeedCardUiModel {
    return FeedCardUiModel(
        title = this.title,
        content = this.description,
        createdAt = this.createdAt,
        keywordList = this.keywords.map { it.name },
        thumbnail = this.url.toInt(),
    )
}
