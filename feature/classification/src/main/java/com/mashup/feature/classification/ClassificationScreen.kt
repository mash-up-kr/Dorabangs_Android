package com.mashup.feature.classification

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChips
import com.mashup.dorabangs.core.designsystem.component.chips.FeedUiModel
import com.mashup.dorabangs.core.designsystem.component.topbar.DoraTopBar
import com.mashup.dorabangs.core.designsystem.component.util.LottieLoader
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun ClassificationRoute(
    onClickBackIcon: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToWebView: (String) -> Unit,
    classificationViewModel: ClassificationViewModel = hiltViewModel(),
) {
    val state by classificationViewModel.collectAsState()
    val pagingList = classificationViewModel.paging.collectAsLazyPagingItems()

    ClassificationScreen(
        state = state,
        pagingList = pagingList,
        onClickChip = classificationViewModel::changeCategory,
        onClickDeleteButton = classificationViewModel::deleteSelectedItem,
        onClickMoveButton = classificationViewModel::moveSelectedItem,
        onClickAllItemMoveButton = classificationViewModel::moveAllItems,
        onClickBackIcon = onClickBackIcon,
        navigateToHome = navigateToHome,
        onClickCardItem = { cardInfo ->
            classificationViewModel.updateReadAt(cardInfo)
            navigateToWebView(cardInfo.url)
        },
    )
}

@Composable
fun ClassificationScreen(
    state: ClassificationState,
    pagingList: LazyPagingItems<FeedUiModel>,
    onClickChip: (Int) -> Unit,
    onClickDeleteButton: (FeedUiModel.FeedCardUiModel) -> Unit,
    onClickMoveButton: (FeedUiModel.FeedCardUiModel) -> Unit,
    onClickAllItemMoveButton: (String) -> Unit,
    onClickBackIcon: () -> Unit,
    navigateToHome: () -> Unit,
    onClickCardItem: (FeedUiModel.FeedCardUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val lazyColumnState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        DoraTopBar.BackNavigationTopBar(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(id = R.string.ai_classification_title),
            isTitleCenter = true,
            onClickBackIcon = { onClickBackIcon() },
        )
        DoraChips(
            modifier = modifier.fillMaxWidth(),
            chipList = state.chipState.chipList,
            isShowPostCount = true,
            selectedIndex = state.chipState.currentIndex,
            onClickChip = {
                onClickChip(it) // UI Update
                coroutineScope.launch {
                    lazyColumnState.animateScrollToItem(0)
                }
            },
        )
        if (state.chipState.totalCount == 0) {
            ClassificationCompleteScreen(navigateToHome = navigateToHome)
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally),
            ) {
                ClassificationListScreen(
                    state = state,
                    lazyColumnState = lazyColumnState,
                    pagingList = pagingList,
                    onClickDeleteButton = onClickDeleteButton,
                    onClickMoveButton = onClickMoveButton,
                    onClickAllItemMoveButton = onClickAllItemMoveButton,
                    onClickCardItem = onClickCardItem,
                )

                if (state.isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                            ) { /* no-op */ },
                    ) {
                        LottieLoader(
                            lottieRes = com.mashup.dorabangs.core.designsystem.R.raw.spinner,
                            modifier = Modifier
                                .size(54.dp)
                                .align(Alignment.Center),
                            iterations = 200,
                            reverseOnRepeat = true,
                        )
                    }
                }
            }
        }
    }
}
