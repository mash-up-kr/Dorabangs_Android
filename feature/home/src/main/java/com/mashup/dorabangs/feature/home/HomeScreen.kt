package com.mashup.dorabangs.feature.home

import android.view.View
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.component.bottomsheet.DoraBottomSheet
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
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import com.mashup.dorabangs.core.designsystem.theme.DoraGradientToken
import com.mashup.dorabangs.core.designsystem.theme.DoraRoundTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild


@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    clipboardManager: ClipboardManager = LocalClipboardManager.current,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToClassification: () -> Unit = {},
    navigateToSaveScreenWithLink: (String) -> Unit = {},
    navigateToSaveScreenWithoutLink: () -> Unit = {},
    navigateToCreateFolder: () -> Unit,
) {
    val snackBarHostState by remember { mutableStateOf(SnackbarHostState()) }
    val state by viewModel.collectAsState()
    val scope = rememberCoroutineScope()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is HomeSideEffect.ShowSnackBar -> {
                scope.launch {
                    snackBarHostState.showSnackbar(
                        message = sideEffect.copiedText,
                        duration = SnackbarDuration.Indefinite,
                    )
                }
            }
            is HomeSideEffect.HideSnackBar -> {
                snackBarHostState.currentSnackbarData?.dismiss()
            }
            is HomeSideEffect.NavigateToCreateFolder -> navigateToCreateFolder()
        }
    }

    Box {
        HomeScreen(
            state = state,
            modifier = modifier,
            onClickChip = viewModel::changeSelectedTapIdx,
            navigateToClassification = navigateToClassification,
            onClickMoreButton = {
                viewModel.setVisibleMoreButtonBottomSheet(true)
            },
            navigateSaveScreenWithoutLink = navigateToSaveScreenWithoutLink,
        )

        HomeDoraSnackBar(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            text = state.clipBoardState.copiedText,
            onAction = { url ->
                viewModel.setLocalCopiedUrl(url = url)
                if (state.clipBoardState.isValidUrl) {
                    navigateToSaveScreenWithLink.invoke(url)
                }
            },
            snackBarHostState = snackBarHostState,
            view = view,
            clipboardManager = clipboardManager,
            lastCopiedText = { viewModel.getLocalCopiedUrl().orEmpty() },
            hideSnackBar = viewModel::hideSnackBar,
            showSnackBarWithText = viewModel::showSnackBar,
            dismissAction = { url ->
                viewModel.setLocalCopiedUrl(url = url)
                viewModel.hideSnackBar()
            },
        )

        DoraBottomSheet.MoreButtonBottomSheet(
            modifier = Modifier.height(320.dp),
            isShowSheet = state.isShowMoreButtonSheet,
            firstItemName = R.string.more_button_bottom_sheet_remove_link,
            secondItemName = R.string.more_button_bottom_sheet_moving_folder,
            onClickDeleteLinkButton = {
                viewModel.setVisibleMoreButtonBottomSheet(false)
                viewModel.setVisibleDialog(true)
            },
            onClickMoveFolderButton = {
                viewModel.setVisibleMoreButtonBottomSheet(false)
                viewModel.setVisibleMovingFolderBottomSheet(true)
            },
            onDismissRequest = { viewModel.setVisibleMoreButtonBottomSheet(false) },
        )

        DoraBottomSheet.MovingFolderBottomSheet(
            modifier = Modifier.height(441.dp),
            isShowSheet = state.isShowMovingFolderSheet,
            folderList = testFolderListData,
            onDismissRequest = { viewModel.setVisibleMovingFolderBottomSheet(false) },
            onClickCreateFolder = { viewModel.setVisibleMovingFolderBottomSheet(visible = false, isNavigate = true) },
            onClickMoveFolder = {},
        )

        DoraDialog(
            isShowDialog = state.isShowDialog,
            title = stringResource(R.string.remove_dialog_title),
            content = stringResource(R.string.remove_dialog_content),
            confirmBtnText = stringResource(R.string.remove_dialog_confirm),
            disMissBtnText = stringResource(R.string.remove_dialog_cancil),
            onDisMissRequest = { viewModel.setVisibleDialog(false) },
            onClickConfirmBtn = { viewModel.setVisibleDialog(false) },
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    state: HomeState,
    modifier: Modifier = Modifier,
    onClickChip: (Int) -> Unit = {},
    navigateToClassification: () -> Unit = {},
    onClickMoreButton: (Int) -> Unit = {},
    navigateSaveScreenWithoutLink: () -> Unit = {},
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

                HomeCarousel(
                    homeCarouselItems = listOf(
                        HomeCarouselItem(
                            lottieRes = R.raw.unread,
                            description = "3초만에 링크를\n저장하는 방법이에요"
                        ),
                        HomeCarouselItem(
                            lottieRes = R.raw.ai,
                            description = "AI로 분류 링크가\n375개 있어요"
                        )
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
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

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun HomeCarousel(
    homeCarouselItems: List<HomeCarouselItem>,
    modifier: Modifier = Modifier,
) {
    val pagerState: PagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { homeCarouselItems.size }
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPager(
            modifier = Modifier.background(
                brush = DoraGradientToken.Gradient3,
            ),
            state = pagerState
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
                        .height(212.dp)
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
                        onClick = homeCarouselItems[page].onClickButton
                    ) {
                        Text(
                            text = "확인하기",
                            style = DoraTypoTokens.caption1Bold,
                            color = DoraColorTokens.G7,
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_right_arrow_black_12dp),
                            tint = DoraColorTokens.G7,
                            contentDescription = ""
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
                    shape = DoraRoundTokens.Round99
                ),
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                repeat(pagerState.pageCount) { index ->
                    Box(
                        modifier = Modifier
                            .size(4.dp)
                            .clip(CircleShape)
                            .background(DoraColorTokens.G4)
                            .thenIf(index == pagerState.currentPage) {
                                background(brush = DoraGradientToken.Gradient5)
                            }
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
