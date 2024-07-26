package com.mashup.dorabangs.feature.storage.storagedetail.model

import com.mashup.dorabangs.core.designsystem.component.bottomsheet.SelectableBottomSheetItemUIModel
import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.domain.model.Post
import com.mashup.dorabangs.domain.model.SavedLinkDetailInfo
import com.mashup.dorabangs.core.designsystem.R as coreR

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

fun Folder.toBottomSheetModel(currentFolderId: String): SelectableBottomSheetItemUIModel {
    return SelectableBottomSheetItemUIModel(
        icon = coreR.drawable.ic_3d_folder_small,
        itemName = name,
        isSelected = id == currentFolderId,
    )
}