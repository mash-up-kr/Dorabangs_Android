package com.mashup.dorabangs.feature.home

import com.mashup.dorabangs.core.designsystem.component.bottomsheet.SelectableBottomSheetItemUIModel
import com.mashup.dorabangs.core.designsystem.component.chips.FeedUiModel
import com.mashup.dorabangs.core.designsystem.component.toast.ToastStyle
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.domain.utils.isValidUrl
import com.mashup.dorabangs.core.designsystem.R as coreR

data class HomeState(
    val isLoading: Boolean = false,
    val isScrollLoading: Boolean = false,
    val clipBoardState: ClipBoardState = ClipBoardState(),
    val tapElements: List<FeedUiModel.DoraChipUiModel> = emptyList(),
    val folderList: List<Folder> = listOf(),
    val selectedIndex: Int = 0,
    val selectedPostId: String = "",
    val selectedFolderId: String = "",
    val changeFolderId: String = selectedFolderId,
    val changeFolderName: String = "",
    val isShowMoreButtonSheet: Boolean = false,
    val isShowDialog: Boolean = false,
    val hasShowToastState: Boolean = false,
    val isShowMovingFolderSheet: Boolean = false,
    val homeCreateFolder: HomeCreateFolder = HomeCreateFolder(),
    val aiClassificationCount: Int = 0,
    val toastState: ToastState = ToastState(),
    val unReadPostCount: Int = 0,
    val isEditPostFolder: Boolean = false,
    val allFolder: Folder? = null,
    val isNeedToRefreshOnStart: Boolean = true,
    val postList: List<FeedUiModel.FeedCardUiModel> = emptyList(),
) {
    companion object {
        // TODO - 추후 sotrageMapper와 합치기
        fun List<Folder>.toSelectBottomSheetModel(folderId: String): List<SelectableBottomSheetItemUIModel> {
            return this.map { item ->
                SelectableBottomSheetItemUIModel(
                    id = item.id.orEmpty(),
                    icon = coreR.drawable.ic_3d_folder_big,
                    itemName = item.name,
                    isSelected = item.id == folderId,
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

data class ToastState(
    val text: String = "",
    val toastStyle: ToastStyle = ToastStyle.CHECK,
)
