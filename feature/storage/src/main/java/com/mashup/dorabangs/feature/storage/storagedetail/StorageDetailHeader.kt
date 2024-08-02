package com.mashup.dorabangs.feature.storage.storagedetail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.component.topbar.DoraTopBar
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraRoundTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
import com.mashup.dorabangs.domain.model.FolderType
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailState
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailTab
import com.mashup.dorabangs.util.getFolderIcon
import com.mashup.dorabangs.core.designsystem.R as coreR

@Composable
fun StorageDetailCollapsingHeader(
    state: StorageDetailState,
    isCollapsed: Boolean,
    onClickBackIcon: () -> Unit,
    onClickTabItem: (Int) -> Unit,
    onClickActionIcon: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.then(
            Modifier
                .background(color = DoraColorTokens.White)
                .height(MinToolbarHeight),
        ),
    ) {
        if (isCollapsed) {
            StorageDetailTopBarByFolderType(
                state = state,
                isCollapsed = true,
                onClickBackIcon = onClickBackIcon,
                onClickActionIcon = onClickActionIcon,
            )
            StorageDetailHeaderTabBar(
                tabList = state.tabInfo.tabTitleList,
                onClickTabItem = onClickTabItem,
                selectedTabIdx = state.tabInfo.selectedTabIdx,
            )
        }
    }
}

@Composable
fun StorageDetailExpandedHeader(
    state: StorageDetailState,
    onClickTabItem: (Int) -> Unit,
    onClickBackIcon: () -> Unit,
    onClickActionIcon: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.height(MaxToolbarHeight),
    ) {
        StorageDetailTopBarByFolderType(
            state = state,
            isCollapsed = false,
            onClickBackIcon = onClickBackIcon,
            onClickActionIcon = onClickActionIcon,
        )
        StorageDetailHeaderContent(
            state = state,
        )
        StorageDetailHeaderTabBar(
            tabList = state.tabInfo.tabTitleList,
            selectedTabIdx = state.tabInfo.selectedTabIdx,
            onClickTabItem = onClickTabItem,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(DoraColorTokens.G2),
        )
    }
}

@Composable
fun StorageDetailHeaderContent(
    state: StorageDetailState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(color = DoraColorTokens.P1)
            .padding(horizontal = 20.dp, vertical = 6.dp),
    ) {
        Box(
            modifier = Modifier.background(
                color = DoraColorTokens.G1,
                shape = DoraRoundTokens.Round8,
            )
                .size(54.dp),
        ) {
            Image(
                modifier = Modifier
                    .padding(6.dp)
                    .size(40.dp)
                    .aspectRatio(1f),
                painter = painterResource(id = getFolderIcon(state.folderInfo.folderType)),
                contentDescription = "상단바 아이콘",
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .fillMaxWidth(),
        ) {
            Text(
                text = state.folderInfo.title,
                style = DoraTypoTokens.Subtitle2Bold,
            )
            Text(
                text = "${state.folderInfo.postCount} 게시물",
                style = DoraTypoTokens.caption1Medium,
            )
        }
    }
}

@Composable
fun StorageDetailHeaderTabBar(
    tabList: List<StorageDetailTab>,
    onClickTabItem: (Int) -> Unit,
    modifier: Modifier = Modifier,
    selectedTabIdx: Int = 0,
) {
    Column {
        Row(
            modifier = modifier.height(48.dp).fillMaxWidth(),
        ) {
            tabList.forEachIndexed { index, tabItem ->
                val isSelected = index == selectedTabIdx
                Column(
                    modifier = Modifier
                        .clickable { onClickTabItem(index) }
                        .padding(vertical = 9.5.dp),
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        text = stringResource(id = tabItem.tabTitle),
                        style = DoraTypoTokens.caption2Medium,
                        color = if (isSelected) DoraColorTokens.G9 else DoraColorTokens.G4,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    if (isSelected) {
                        Canvas(
                            Modifier
                                .size(3.dp)
                                .align(Alignment.CenterHorizontally),
                        ) {
                            drawCircle(color = Color.Black)
                        }
                    }
                }
                if (index == 0) {
                    VerticalDivider(
                        modifier = Modifier.padding(top = 14.dp, bottom = 22.dp),
                        color = DoraColorTokens.G2,
                    )
                }
            }
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = DoraColorTokens.G2,
        )
    }
}

@Composable
fun StorageDetailTopBarByFolderType(
    state: StorageDetailState,
    isCollapsed: Boolean,
    onClickActionIcon: () -> Unit = {},
    onClickBackIcon: () -> Unit,
) {
    val title = if (isCollapsed) state.folderInfo.title else ""
    when (state.folderInfo.folderType) {
        FolderType.CUSTOM -> {
            DoraTopBar.BackWithActionIconTopBar(
                modifier = Modifier.fillMaxWidth(),
                title = title,
                isTitleCenter = true,
                actionIcon = coreR.drawable.ic_more_black,
                onClickBackIcon = onClickBackIcon,
                onClickActonIcon = onClickActionIcon,
            )
        }
        else -> {
            DoraTopBar.BackNavigationTopBar(
                modifier = Modifier.fillMaxWidth(),
                title = title,
                isTitleCenter = true,
                onClickBackIcon = onClickBackIcon,
            )
        }
    }
}

@Preview
@Composable
fun PreviewStorageDetailCollapsingTopBar() {
    StorageDetailHeaderTabBar(
        tabList = StorageDetailState.getDefaultTabTitleList(),
        selectedTabIdx = 0,
        onClickTabItem = {},
    )
}
