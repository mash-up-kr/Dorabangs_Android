package com.mashup.dorabangs.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.component.bottomsheet.SelectableBottomSheetItemUIModel
import com.mashup.dorabangs.core.designsystem.component.card.FeedCard
import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChipUiModel
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChips
import com.mashup.dorabangs.core.designsystem.component.topbar.DoraTopBar
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

@Composable
fun HomeScreen(
    state: HomeState,
    modifier: Modifier = Modifier,
    onClickChip: (Int) -> Unit = {},
    navigateToClassification: () -> Unit = {},
    onClickMoreButton: (Int) -> Unit = {},
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        val hazeState = remember { HazeState() }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .haze(hazeState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Spacer(
                    modifier = Modifier.height(104.dp),
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(310.dp)
                        .background(DoraColorTokens.G5)
                        .clickable { navigateToClassification() },
                )
            }

            Feeds(
                modifier = Modifier,
                feeds = state.feedCards,
                onClickMoreButton = { index ->
                    onClickMoreButton(index)
                },
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(104.dp)
                .hazeChild(
                    state = hazeState,
                    style = HazeStyle(blurRadius = 12.dp),
                ),
        )

        Column {
            DoraTopBar.HomeTopBar(
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(),
                title = "Logo",
                actionIcon = R.drawable.ic_add_link,
                onClickActonIcon = {},
            )

            DoraChips(
                modifier = Modifier.fillMaxWidth(),
                chipList = state.tapElements,
                selectedIndex = state.selectedIndex,
                onClickChip = {
                    onClickChip(it)
                },
            )
        }
    }
}

private fun LazyListScope.Feeds(
    feeds: List<FeedCardUiModel>,
    modifier: Modifier = Modifier,
    onClickMoreButton: (Int) -> Unit = {},
) {
    if (feeds.isEmpty()) {
        item {
            Column(
                modifier = modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_android_white_24dp),
                    contentDescription = "",
                )
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = stringResource(id = R.string.home_empty_feed),
                    style = DoraTypoTokens.caption3Medium,
                    color = DoraColorTokens.G3,
                )
            }
        }
    } else {
        items(feeds.size) { index ->
            FeedCard(
                cardInfo = feeds[index],
                onClickMoreButton = {
                    onClickMoreButton(index)
                },
            )
            if (index != feeds.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 24.dp, horizontal = 20.dp),
                    thickness = 0.5.dp,
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        state = HomeState(
            tapElements = listOf(
                DoraChipUiModel(
                    title = "전체",
                    icon = R.drawable.ic_plus,
                ),
                DoraChipUiModel(
                    title = "즐겨찾기",
                    icon = R.drawable.ic_plus,
                ),
                DoraChipUiModel(
                    title = "나중에 읽을 링크",
                    icon = R.drawable.ic_plus,
                ),
                DoraChipUiModel(
                    title = "테스트",
                ),
                DoraChipUiModel(
                    title = "테스트",
                ),
                DoraChipUiModel(
                    title = "테스트",
                ),
            ),
        ),
    )
}

val testFolderListData = listOf(
    SelectableBottomSheetItemUIModel(
        R.drawable.ic_plus,
        "폴더이름",
        false,
    ),
    SelectableBottomSheetItemUIModel(
        R.drawable.ic_plus,
        "폴더이름",
        false,
    ),
    SelectableBottomSheetItemUIModel(
        R.drawable.ic_plus,
        "폴더이름",
        true,
    ),
    SelectableBottomSheetItemUIModel(
        R.drawable.ic_plus,
        "폴더이름",
        false,
    ),
)
