package com.mashup.dorabangs.feature.home

import android.view.View
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
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
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.component.card.FeedCard
import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChipUiModel
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChips
import com.mashup.dorabangs.core.designsystem.component.topbar.DoraTopAppBar
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    view: View = LocalView.current,
    clipboardManager: ClipboardManager = LocalClipboardManager.current,
    viewModel: HomeViewModel = hiltViewModel(),
    actionSnackBar: () -> Unit = {},
    navigateToClassification: () -> Unit = {},
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

            HomeSideEffect.HideSnackBar -> {
                clipboardManager.setText(AnnotatedString(""))
                snackBarHostState.currentSnackbarData?.dismiss()
            }
        }
    }

    Box {
        HomeScreen(
            state = state,
            modifier = modifier,
            onClickChip = viewModel::changeSelectedTapIdx,
            navigateToClassification = navigateToClassification,
        )

        HomeDoraSnackBar(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            text = state.clipBoardState.copiedText,
            action = actionSnackBar,
            snackBarHostState = snackBarHostState,
            view = view,
            clipboardManager = clipboardManager,
            hideSnackBar = viewModel::hideSnackBar,
            showSnackBarWithText = viewModel::showSnackBar,
            dismissAction = viewModel::hideSnackBar,
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
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            DoraTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = "Logo",
                isTitleCenter = false,
                actionIcon = R.drawable.ic_plus,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(310.dp)
                    .background(DoraColorTokens.G5)
                    .clickable { navigateToClassification() },
            )
        }

        stickyHeader {
            DoraChips(
                modifier = Modifier.fillMaxWidth(),
                chipList = state.tapElements,
                selectedIndex = state.selectedIndex,
                onClickChip = {
                    onClickChip(it)
                },
            )
        }

        Feeds(
            modifier = Modifier,
            feeds = state.feedCards,
        )
    }
}

private fun LazyListScope.Feeds(
    feeds: List<FeedCardUiModel>,
    modifier: Modifier = Modifier,
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
                )
            }
        }
    } else {
        items(feeds.size) {
            FeedCard(cardInfo = feeds[it])
            if (it != feeds.lastIndex) {
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
                    title = "전체 99+",
                    icon = R.drawable.ic_plus,
                ),
                DoraChipUiModel(
                    title = "하이?",
                ),
                DoraChipUiModel(
                    title = "바이?",
                ),
                DoraChipUiModel(
                    title = "바이?",
                ),
                DoraChipUiModel(
                    title = "바이?",
                    icon = R.drawable.ic_plus,
                ),
            ),
        ),
    )
}
