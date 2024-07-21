package com.mashup.dorabangs.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.component.bottomsheet.SelectableBottomSheetItemUIModel
import com.mashup.dorabangs.core.designsystem.component.buttons.GradientButton
import com.mashup.dorabangs.core.designsystem.component.card.FeedCard
import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChipUiModel
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChips
import com.mashup.dorabangs.core.designsystem.component.topbar.DoraTopBar
import com.mashup.dorabangs.core.designsystem.component.util.LottieLoader
import com.mashup.dorabangs.core.designsystem.component.util.thenIf
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraGradientToken
import com.mashup.dorabangs.core.designsystem.theme.DoraRoundTokens
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
    onClickMoreButton: (Int) -> Unit = {},
    navigateToClassification: () -> Unit = {},
    navigateSaveScreenWithoutLink: () -> Unit = {},
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        val hazeState = remember { HazeState() }

        if (state.feedCards.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Icon(
                    modifier = Modifier.padding(top = 104.dp),
                    painter = painterResource(id = R.drawable.ic_empty),
                    contentDescription = "",
                    tint = Color.Unspecified,
                )
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = stringResource(id = R.string.home_empty_feed),
                    style = DoraTypoTokens.caption3Medium,
                    color = DoraColorTokens.G3,
                )
            }
        } else {
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

                    HomeCarousel(
                        homeCarouselItems = listOf(
                            HomeCarouselItem(
                                lottieRes = R.raw.ai,
                                description = "AI로 분류 링크가\n375개 있어요",
                                onClickButton = navigateToClassification,
                            ),
                            HomeCarouselItem(
                                lottieRes = R.raw.unread,
                                description = "3초만에 링크를\n저장하는 방법이에요",
                                onClickButton = navigateToClassification,
                            ),
                        ),
                        modifier = Modifier
                            .fillMaxWidth(),
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
        }

        Column {
            DoraTopBar.HomeTopBar(
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(),
                title = "Logo",
                actionIcon = R.drawable.ic_add_link,
                onClickActonIcon = navigateSaveScreenWithoutLink,
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


@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun HomeCarousel(
    homeCarouselItems: List<HomeCarouselItem>,
    modifier: Modifier = Modifier,
) {
    val pagerState: PagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { homeCarouselItems.size },
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPager(
            modifier = Modifier.background(
                brush = DoraGradientToken.Gradient3,
            ),
            state = pagerState,
        ) { page ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(340.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LottieLoader(
                    lottieRes = homeCarouselItems[page].lottieRes,
                    modifier = Modifier
                        .width(250.dp)
                        .height(212.dp),
                )

                Column(
                    modifier = Modifier.padding(bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = homeCarouselItems[page].description,
                        style = DoraTypoTokens.Subtitle2Bold, // Todo :: 굵기 더 두꺼운 폰트 추가해서 적용해야 함
                        textAlign = TextAlign.Center,
                    )

                    GradientButton(
                        containerColor = DoraGradientToken.Gradient3,
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                        onClick = homeCarouselItems[page].onClickButton,
                    ) {
                        Text(
                            text = stringResource(id = R.string.home_carousel_checking_button),
                            style = DoraTypoTokens.caption1Bold,
                            color = DoraColorTokens.G7,
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_chevron_right_small_balck),
                            tint = DoraColorTokens.G7,
                            contentDescription = "",
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .background(
                    brush = DoraGradientToken.Gradient1,
                    shape = DoraRoundTokens.Round99,
                ),
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                repeat(pagerState.pageCount) { index ->
                    Box(
                        modifier = Modifier
                            .size(4.dp)
                            .clip(CircleShape)
                            .background(DoraColorTokens.G4)
                            .thenIf(index == pagerState.currentPage) {
                                background(brush = DoraGradientToken.Gradient5)
                            },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeCarouselPrevice() {
    HomeCarousel(homeCarouselItems = listOf(HomeCarouselItem(R.raw.ai, "테스트")))
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
