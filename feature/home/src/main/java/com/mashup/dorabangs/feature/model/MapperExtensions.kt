package com.mashup.dorabangs.feature.model

import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel
import com.mashup.dorabangs.domain.model.AIStatus
import com.mashup.dorabangs.domain.model.Post
import com.mashup.dorabangs.domain.model.SavedLinkDetailInfo

fun Post.toUiModel(category: String?): FeedCardUiModel {
    return FeedCardUiModel(
        postId = this.id,
        folderId = this.folderId,
        title = this.title,
        content = this.description,
        category = category.orEmpty(),
        createdAt = this.createdAt,
        keywordList = listOf(),
        isFavorite = isFavorite,
        thumbnail = thumbnailImgUrl,
        isLoading = this.aiStatus == AIStatus.IN_PROGRESS,
    )
}

fun SavedLinkDetailInfo.toUiModel(category: String?): FeedCardUiModel {
    return FeedCardUiModel(
        postId = this.id ?: "",
        folderId = this.folderId.orEmpty(),
        title = this.title,
        content = this.description,
        createdAt = this.createdAt,
        category = category.orEmpty(),
        keywordList = emptyList(),
        isFavorite = this.isFavorite ?: false,
        thumbnail = thumbnailImgUrl,
        isLoading = this.aiStatus == AIStatus.IN_PROGRESS,
    )
}