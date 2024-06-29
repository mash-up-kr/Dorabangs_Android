package com.mashup.dorabangs.feature.storage.storagedetail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
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
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
import com.mashup.dorabangs.feature.storage.R
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailState
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailTab

@Composable
fun StorageDetailCollapsingHeader(
    state: StorageDetailState,
    isCollapsed: Boolean,
    modifier: Modifier = Modifier,
    onClickBackIcon: () -> Unit = {},
    onClickTabItem: (Int) -> Unit = {},
) {
    Column(
        modifier = modifier.then(
            Modifier.background(color = DoraColorTokens.White)
                    .height(MinToolbarHeight),
            ),
    ) {
        if (isCollapsed) {
            DoraTopBar.BackNavigationTopBar(
                modifier = Modifier.fillMaxWidth(),
                title = state.title,
                titleAlignment = Alignment.Center,
                onClickBackIcon = onClickBackIcon,
            )
            StorageDetailHeaderTabBar(
                tabList = state.tabTitleList,
                onClickTabItem = onClickTabItem,
            )
        }
    }
}

@Composable
fun StorageDetailExpandedHeader(
    state: StorageDetailState,
    modifier: Modifier = Modifier,
    onClickBackIcon: () -> Unit = {},
) {
    Column(
        modifier = Modifier.height(MaxToolbarHeight),
    ) {
        DoraTopBar.BackNavigationTopBar(
            modifier = Modifier.fillMaxWidth(),
            title = "",
            titleAlignment = Alignment.CenterStart,
            onClickBackIcon = onClickBackIcon,
        )
        StorageDetailHeaderContent(
            state = state,
        )
        StorageDetailHeaderTabBar(
            tabList = state.tabTitleList,
            selectedTabIdx = state.selectedIdx,
            onClickTabItem = {},
        )
    }
}

@Composable
fun StorageDetailHeaderContent(state: StorageDetailState) {
    Row(
        modifier = Modifier
                .fillMaxWidth().height(64.dp)
                .background(color = DoraColorTokens.P1)
                .padding(horizontal = 20.dp, vertical = 6.dp),
    ) {
        Image(
            modifier = Modifier
                    .size(52.dp)
                    .aspectRatio(1f),
            painter = painterResource(id = androidx.core.R.drawable.ic_call_answer),
            contentDescription = "나중에 읽을 링크 아이콘",
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth(),
        ) {
            Text(
                text = stringResource(id = R.string.storage_detail_header_title_later_read),
                style = DoraTypoTokens.base2Bold,
            )
            Text(
                text = "${state.postCount} 게시물",
                style = DoraTypoTokens.caption1Medium,
            )
        }
    }
}

@Composable
fun StorageDetailHeaderTabBar(
    tabList: List<StorageDetailTab>,
    selectedTabIdx: Int = 0,
    onClickTabItem: (Int) -> Unit = {},
) {
    Row(
        modifier = Modifier.height(48.dp),
    ) {
        tabList.forEachIndexed { index, tabItem ->
            val isSelected = index == selectedTabIdx
            Column(
                modifier =
                    Modifier
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
        }
    }
}

@Preview
@Composable
fun PreviewStorageDetailCollapsingTopBar() {
    StorageDetailHeaderTabBar(
        tabList = StorageDetailState.getDefaultTabTitleList(),
        selectedTabIdx = 0,
    )
}
