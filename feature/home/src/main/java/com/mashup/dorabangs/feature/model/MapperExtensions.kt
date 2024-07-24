package com.mashup.dorabangs.feature.model

import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel
import com.mashup.dorabangs.domain.model.Post
import com.mashup.dorabangs.domain.model.SavedLinkDetailInfo

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

fun SavedLinkDetailInfo.toUiModel(): FeedCardUiModel {
    return FeedCardUiModel(
        id = this.id ?: "",
        title = this.title,
        content = this.description,
        createdAt = this.createdAt,
        keywordList = emptyList(),
        isFavorite = this.isFavorite ?: false,
        thumbnail = "",
    )
}
