package com.mashup.dorabangs.feature.storage.storagedetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChips
import com.mashup.dorabangs.core.designsystem.component.chips.FeedUiModel
import com.mashup.dorabangs.core.designsystem.component.topbar.DoraTopBar
import com.mashup.dorabangs.domain.model.FolderType
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailState
import com.mashup.dorabangs.core.designsystem.R as coreR

@Composable
fun StorageDetailHeader(
    state: StorageDetailState,
    chipList: List<FeedUiModel.DoraChipUiModel>,
    selectedTabIdx: Int = 0,
    onClickTabItem: (Int) -> Unit,
    onClickBackIcon: () -> Unit,
    modifier: Modifier = Modifier,
    onClickActionIcon: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        StorageDetailTopBarByFolderType(
            state = state,
            onClickActionIcon = onClickActionIcon,
            onClickBackIcon = onClickBackIcon,
        )
        DoraChips(
            modifier = Modifier.fillMaxWidth(),
            chipList = chipList,
            selectedIndex = selectedTabIdx,
            onClickChip = { onClickTabItem(it) },
        )
    }
}

@Composable
fun StorageDetailTopBarByFolderType(
    state: StorageDetailState,
    onClickActionIcon: () -> Unit = {},
    onClickBackIcon: () -> Unit,
) {
    when (state.folderInfo.folderType) {
        FolderType.CUSTOM -> {
            DoraTopBar.BackWithActionIconTopBar(
                modifier = Modifier.fillMaxWidth(),
                title = state.folderInfo.title,
                isTitleCenter = true,
                actionIcon = coreR.drawable.ic_more_black,
                onClickBackIcon = onClickBackIcon,
                onClickActonIcon = onClickActionIcon,
                isShowBottomDivider = false,
            )
        }
        else -> {
            DoraTopBar.BackNavigationTopBar(
                modifier = Modifier.fillMaxWidth(),
                title = state.folderInfo.title,
                isTitleCenter = true,
                onClickBackIcon = onClickBackIcon,
                isShowBottomDivider = false,
            )
        }
    }
}

@Preview
@Composable
fun PreviewStorageDetailCollapsingTopBar() {
    StorageDetailHeader(
        state = StorageDetailState(),
        chipList = StorageDetailState.getDefaultChipList(),
        selectedTabIdx = 0,
        onClickTabItem = {},
        onClickActionIcon = {},
        onClickBackIcon = {},
    )
}
