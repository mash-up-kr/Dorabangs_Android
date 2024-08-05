package com.mashup.dorabangs.feature.model

import com.mashup.dorabangs.core.designsystem.component.chips.FeedUiModel
import com.mashup.dorabangs.domain.model.AIStatus
import com.mashup.dorabangs.domain.model.Post
import com.mashup.dorabangs.domain.model.SavedLinkDetailInfo

fun Post.toUiModel(): FeedUiModel.FeedCardUiModel {
    return FeedUiModel.FeedCardUiModel(
        postId = this.id,
        folderId = this.folderId,
        title = this.title,
        content = this.description,
        createdAt = this.createdAt,
        keywordList = listOf(),
        isFavorite = isFavorite,
        thumbnail = thumbnailImgUrl,
        url = this.url,
        isLoading = this.aiStatus == AIStatus.IN_PROGRESS,
    )
}

fun SavedLinkDetailInfo.toUiModel(): FeedUiModel.FeedCardUiModel {
    return FeedUiModel.FeedCardUiModel(
        postId = this.id ?: "",
        folderId = this.folderId.orEmpty(),
        title = this.title,
        content = this.description,
        createdAt = this.createdAt,
        keywordList = emptyList(),
        isFavorite = this.isFavorite ?: false,
        thumbnail = thumbnailImgUrl,
        url = this.url.orEmpty(),
        isLoading = this.aiStatus == AIStatus.IN_PROGRESS,
    )
}
