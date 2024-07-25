package com.mashup.dorabangs.feature.home

import com.mashup.dorabangs.core.designsystem.component.bottomsheet.SelectableBottomSheetItemUIModel
import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChipUiModel
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.domain.utils.isValidUrl
import com.mashup.dorabangs.core.designsystem.R as coreR

data class HomeState(
    val clipBoardState: ClipBoardState = ClipBoardState(),
    val tapElements: List<DoraChipUiModel> = emptyList(),
    val feedCards: List<FeedCardUiModel> = emptyList(),
    val folderList: List<Folder> = listOf(),
    val selectedIndex: Int = 0,
    val isShowMoreButtonSheet: Boolean = true,
    val isShowDialog: Boolean = false,
    val isShowMovingFolderSheet: Boolean = false,
    val homeCreateFolder: HomeCreateFolder = HomeCreateFolder(),
    val aiClassificationCount: Int = 0,
) {
    companion object {
        fun List<Folder>.toSelectBottomSheetModel(): List<SelectableBottomSheetItemUIModel> {
            return this.map { item ->
                SelectableBottomSheetItemUIModel(
                    icon = coreR.drawable.ic_3d_folder_big,
                    itemName = item.name,
                    isSelected = false, // TODO - folderId 같은지 체크
                )
            }
        }
    }
}

data class ClipBoardState(
    val copiedText: String = "",
) {
    val isValidUrl = copiedText.isNotBlank() && copiedText.isValidUrl()
}

data class HomeCreateFolder(
    val folderName: String = "",
    val helperEnable: Boolean = false,
    val helperMessage: String = "",
    val urlLink: String = "",
    val lastCheckedFolderName: String = "",
)
