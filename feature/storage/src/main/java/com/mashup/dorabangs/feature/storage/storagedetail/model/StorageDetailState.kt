package com.mashup.dorabangs.feature.storage.storagedetail.model

import androidx.paging.PagingData
import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel
import com.mashup.dorabangs.domain.model.Post
import com.mashup.dorabangs.domain.model.SavedLinkDetailInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class StorageDetailState(
    val folderId: String? = "",
    val title: String = "",
    val postCount: Int = 0,
    val folderType: String = "",
    val tabTitleList: List<StorageDetailTab> = getDefaultTabTitleList(),
    val selectedTabIdx: Int = 0,
    val isLatestSort: StorageDetailSort = StorageDetailSort.ASC,
    val pagingList: Flow<PagingData<FeedCardUiModel>> = emptyFlow(),
) {
    companion object {
        fun getDefaultTabTitleList() = listOf(StorageDetailTab.ALL, StorageDetailTab.UNREAD)
    }
}

fun SavedLinkDetailInfo.toUiModel(): FeedCardUiModel {
    return FeedCardUiModel(
        id = this.id.orEmpty(),
        title = this.title,
        content = this.description,
        createdAt = this.createdAt,
        keywordList = this.keywords?.map { it.name },
        isFavorite = isFavorite ?: false,
        thumbnail = "",
    )
}

fun Post.toUiModel(): FeedCardUiModel {
    return FeedCardUiModel(
        id = this.id,
        title = this.title,
        content = this.description,
        createdAt = this.createAt,
        keywordList = listOf(),
        isFavorite = isFavorite,
        thumbnail = "",
    )
}
