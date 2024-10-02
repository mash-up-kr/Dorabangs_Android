package com.mashup.dorabangs.feature.storage.storagedetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                actionIcon = {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 20.dp)
                            .clickable { onClickActionIcon() },
                        painter = painterResource(id = coreR.drawable.ic_more_black),
                        contentDescription = "action",
                    )
                },
                onClickBackIcon = onClickBackIcon,
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
