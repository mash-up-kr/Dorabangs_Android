package com.mashup.dorabangs.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.component.buttons.BannerButton
import com.mashup.dorabangs.core.designsystem.component.card.FeedCard
import com.mashup.dorabangs.core.designsystem.component.card.FeedCardEntryPoint
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChips
import com.mashup.dorabangs.core.designsystem.component.chips.FeedUiModel
import com.mashup.dorabangs.core.designsystem.component.divider.DoraDivider
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
    postsList: List<FeedUiModel.FeedCardUiModel>,
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState(),
    onClickCardItem: (FeedUiModel.FeedCardUiModel) -> Unit,
    onClickChip: (Int) -> Unit = {},
    onClickMoreButton: (String, String) -> Unit = { _, _ -> },
    onClickBookMarkButton: (String, Boolean) -> Unit = { _, _ -> },
    navigateToClassification: () -> Unit = {},
    navigateSaveScreenWithoutLink: () -> Unit = {},
    navigateToHomeTutorial: () -> Unit = {},
    requestUpdate: (String) -> Unit = {},
    navigateToUnreadStorageDetail: () -> Unit = {},
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        val hazeState = remember { HazeState() }

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 104.dp)
                    .align(Alignment.Center),
            ) {
                LottieLoader(
                    lottieRes = R.raw.spinner,
                    modifier = Modifier
                        .size(54.dp)
                        .align(Alignment.Center),
                )
            }
        } else if (postsList.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(104.dp))

                if (state.selectedIndex == 0) {
                    HomeCarousel(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp, horizontal = 20.dp)
                            .clip(DoraRoundTokens.Round20),
                        homeCarouselItems = listOf(
                            HomeCarouselItem(
                                lottieRes = R.raw.unread,
                                description = stringResource(id = R.string.home_carousel_save_introduce),
                                onClickButton = navigateToHomeTutorial,
                            ),
                        ),
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Icon(
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
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .haze(hazeState),
                state = scrollState,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    Spacer(
                        modifier = Modifier.height(104.dp),
                    )

                    if (state.selectedIndex == 0) {
                        HomeCarousel(
                            homeCarouselItems = listOf(
                                HomeCarouselItem(
                                    lottieRes = R.raw.ai,
                                    indicatorIcon = R.drawable.ic_ai_8dp,
                                    description = stringResource(R.string.home_carousel_classified_link_as_ai, state.aiClassificationCount),
                                    onClickButton = navigateToClassification,
                                    isVisible = state.aiClassificationCount > 0,
                                ),
                                HomeCarouselItem(
                                    lottieRes = R.raw.unread,
                                    description = stringResource(R.string.home_carousel_not_read_yet, state.unReadPostCount),
                                    onClickButton = navigateToUnreadStorageDetail,
                                    isVisible = state.unReadPostCount > 0,
                                ),
                                HomeCarouselItem(
                                    lottieRes = R.raw.tutorial,
                                    description = stringResource(id = R.string.home_carousel_save_introduce),
                                    onClickButton = navigateToHomeTutorial,
                                ),
                            ).filter { it.isVisible },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp, horizontal = 20.dp)
                                .clip(DoraRoundTokens.Round20),
                        )
                    }
                }

                Feeds(
                    feeds = postsList,
                    onClickMoreButton = { postId, folderId ->
                        onClickMoreButton(postId, folderId)
                    },
                    onClickBookMarkButton = { postId, isFavorite ->
                        onClickBookMarkButton(
                            postId,
                            isFavorite,
                        )
                    },
                    requestUpdate = { requestUpdate.invoke(it) },
                    onClickCardItem = onClickCardItem,
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(101.dp)
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
    feeds: List<FeedUiModel.FeedCardUiModel>,
    onClickMoreButton: (String, String) -> Unit,
    onClickBookMarkButton: (String, Boolean) -> Unit,
    onClickCardItem: (FeedUiModel.FeedCardUiModel) -> Unit,
    requestUpdate: (String) -> Unit,
) {
    items(
        count = feeds.size,
        contentType = { index -> feeds[index].isLoading },
    ) { index ->
        val cardInfo = feeds[index]
        FeedCard(
            cardInfo = cardInfo,
            feedCardEntryPoint = FeedCardEntryPoint.Home,
            onClickMoreButton = {
                onClickMoreButton(cardInfo.postId, cardInfo.folderId)
            },
            onClickBookMarkButton = {
                onClickBookMarkButton(
                    cardInfo.postId,
                    cardInfo.isFavorite.not(),
                )
            },
            requestUpdate = { requestUpdate.invoke(it) },
            onClickCardItem = onClickCardItem,
        )

        if (index != feeds.lastIndex) {
            DoraDivider(modifier = Modifier.padding(horizontal = 20.dp))
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

    Box(
        modifier = modifier,
    ) {
        HorizontalPager(
            modifier = Modifier.background(
                brush = DoraGradientToken.Gradient2,
            ),
            state = pagerState,
        ) { page ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(334.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LottieLoader(
                    lottieRes = homeCarouselItems[page].lottieRes,
                    modifier = Modifier
                        .width(270.dp)
                        .height(210.dp),
                )

                Column(
                    modifier = Modifier.padding(bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        modifier = Modifier.height(56.dp),
                        text = homeCarouselItems[page].description,
                        style = DoraTypoTokens.Subtitle2Bold,
                        textAlign = TextAlign.Center,
                    )
                    BannerButton(
                        containerColor = DoraColorTokens.G7,
                        contentPadding = PaddingValues(start = 16.dp, end = 12.dp, top = 8.dp, bottom = 8.dp),
                        gradientModifier = Modifier.defaultMinSize(
                            minWidth = ButtonDefaults.MinWidth,
                            minHeight = ButtonDefaults.MinHeight,
                        ),
                        onClick = homeCarouselItems[page].onClickButton,
                    ) {
                        Text(
                            text = stringResource(id = R.string.home_carousel_checking_button),
                            style = DoraTypoTokens.caption1Medium,
                            color = DoraColorTokens.White,
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_chevron_right_small_white),
                            contentDescription = "",
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 20.dp, end = 20.dp)
                    .background(
                        brush = DoraGradientToken.Gradient3,
                        shape = DoraRoundTokens.Round99,
                    ),
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    repeat(pagerState.pageCount) { index ->
                        if (homeCarouselItems[index].indicatorIcon == null) {
                            Box(
                                modifier = Modifier
                                    .size(4.dp)
                                    .clip(CircleShape)
                                    .background(DoraColorTokens.G4)
                                    .thenIf(index == pagerState.currentPage) {
                                        background(color = DoraColorTokens.G7)
                                    },
                            )
                        } else {
                            if (index == pagerState.currentPage) {
                                Icon(
                                    painter = painterResource(
                                        id = homeCarouselItems[index].indicatorIcon
                                            ?: R.drawable.ic_empty,
                                    ),
                                    contentDescription = "",
                                    tint = DoraColorTokens.G7,
                                )
                            } else {
                                Icon(
                                    painter = painterResource(
                                        id = homeCarouselItems[index].indicatorIcon
                                            ?: R.drawable.ic_empty,
                                    ),
                                    contentDescription = "",
                                    modifier = Modifier.size(8.dp),
                                    tint = DoraColorTokens.G4,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GradientIcon(
    painter: Painter,
    contentDescription: String,
    brushGradient: Brush,
    modifier: Modifier = Modifier,
) {
    Icon(
        modifier = modifier
            .graphicsLayer(alpha = 0.99f)
            .drawWithCache {
                onDrawWithContent {
                    drawContent()
                    drawRect(brushGradient, blendMode = BlendMode.SrcAtop)
                }
            },
        painter = painter,
        contentDescription = contentDescription,
    )
}

@Preview
@Composable
fun HomeCarouselPreview() {
    HomeCarousel(
        homeCarouselItems = listOf(
            HomeCarouselItem(
                lottieRes = R.raw.ai,
                indicatorIcon = R.drawable.ic_ai_8dp,
                description = stringResource(R.string.home_carousel_classified_link_as_ai, 300),
                onClickButton = {},
                isVisible = true,
            ),
        ),
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    val postList = remember { mutableStateListOf<FeedUiModel.FeedCardUiModel>() }
    HomeScreen(
        onClickMoreButton = { postId, folderId -> },
        onClickCardItem = {},
        state = HomeState(
            tapElements = listOf(
                FeedUiModel.DoraChipUiModel(
                    title = "전체",
                    icon = R.drawable.ic_plus,
                ),
                FeedUiModel.DoraChipUiModel(
                    title = "즐겨찾기",
                    icon = R.drawable.ic_plus,
                ),
                FeedUiModel.DoraChipUiModel(
                    title = "나중에 읽을 링크",
                    icon = R.drawable.ic_plus,
                ),
                FeedUiModel.DoraChipUiModel(
                    title = "테스트",
                ),
                FeedUiModel.DoraChipUiModel(
                    title = "테스트",
                ),
                FeedUiModel.DoraChipUiModel(
                    title = "테스트",
                ),
            ),
        ),
        postsList = postList,
    )
}
