package com.mashup.dorabangs.feature.storage.storagedetail.model

import androidx.annotation.StringRes
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.component.toast.ToastStyle
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.domain.model.FolderType

data class StorageDetailState(
    val folderInfo: FolderInfo = FolderInfo(),
    val tabInfo: TabInfo = TabInfo(),
    val moreBottomSheetState: MoreBottomSheetState = MoreBottomSheetState(),
    val isShowMovingFolderSheet: Boolean = false,
    val editDialogState: EditDialogState = EditDialogState(),
    val isLatestSort: StorageDetailSort = StorageDetailSort.ASC,
    val editActionType: EditActionType = EditActionType.FolderEdit,
    val currentClickPostId: String = "",
    val changeClickFolderId: String = folderInfo.folderId.orEmpty(),
    val folderList: List<Folder> = listOf(),
    val toastState: ToastState = ToastState(),

) {
    companion object {
        fun getDefaultTabTitleList() = listOf(StorageDetailTab.ALL, StorageDetailTab.UNREAD)
    }
}
data class TabInfo(
    val tabTitleList: List<StorageDetailTab> = StorageDetailState.getDefaultTabTitleList(),
    val selectedTabIdx: Int = 0,
)

data class FolderInfo(
    val folderId: String? = "",
    val title: String = "",
    val postCount: Int = 0,
    val folderType: FolderType = FolderType.ALL,
)

data class MoreBottomSheetState(
    val isShowMoreButtonSheet: Boolean = false,
    @StringRes val firstItem: Int = R.string.more_button_bottom_sheet_remove_link,
    @StringRes val secondItem: Int = R.string.more_button_bottom_sheet_moving_folder,
)

data class EditDialogState(
    val isShowDialog: Boolean = false,
    @StringRes val dialogTitle: Int = R.string.remove_dialog_folder_title,
    @StringRes val dialogCont: Int = R.string.remove_dialog_folder_cont,
)

data class ToastState(
    val text: String = "",
    val toastStyle: ToastStyle = ToastStyle.CHECK,
)
