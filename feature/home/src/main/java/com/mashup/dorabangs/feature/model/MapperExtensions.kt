package com.mashup.dorabangs.feature.model

import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel
import com.mashup.dorabangs.domain.model.Post

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
