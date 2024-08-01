package com.mashup.feature.classification

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChipUiModel
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChips
import com.mashup.dorabangs.core.designsystem.component.topbar.DoraTopBar
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun ClassificationRoute(
    onClickBackIcon: () -> Unit,
    navigateToHome: () -> Unit,
    classificationViewModel: ClassificationViewModel = hiltViewModel(),
) {
    val state by classificationViewModel.collectAsState()
    val pagingList = state.cardInfoList.collectAsLazyPagingItems()

    ClassificationScreen(
        state = state,
        pagingList = pagingList,
        onClickChip = classificationViewModel::changeCategory,
        onClickDeleteButton = classificationViewModel::deleteSelectedItem,
        onClickMoveButton = classificationViewModel::moveSelectedItem,
        onClickAllItemMoveButton = classificationViewModel::moveAllItems,
        onClickBackIcon = onClickBackIcon,
        navigateToHome = navigateToHome,
    )
}

@Composable
fun ClassificationScreen(
    state: ClassificationState,
    pagingList: LazyPagingItems<FeedCardUiModel>,
    onClickChip: (Int) -> Unit,
    onClickDeleteButton: (FeedCardUiModel) -> Unit,
    onClickMoveButton: (Int) -> Unit,
    onClickAllItemMoveButton: () -> Unit,
    onClickBackIcon: () -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val totalCount = if (state.chipState.totalCount > 99) "99+" else state.chipState.totalCount.toString()
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
            chipList = listOf(
                DoraChipUiModel(
                    id = "",
                    title = stringResource(R.string.ai_classification_chips_count, totalCount),
                    postCount = state.chipState.totalCount,
                    icon = com.mashup.dorabangs.core.designsystem.R.drawable.ic_3d_all_small,
                ),
            ) + state.chipState.chipList,
            selectedIndex = state.chipState.currentIndex,
            onClickChip = {
                onClickChip(it) // UI Update
                coroutineScope.launch {
                    // 여기서 선택한 칩의 scroll state 구분해줄 방법 만들기
                    lazyColumnState.animateScrollToItem(0)
                }
            },
        )
        if (state.chipState.totalCount == 0) {
            ClassificationCompleteScreen(navigateToHome = navigateToHome)
        } else {
            ClassificationListScreen(
                state = state,
                lazyColumnState = lazyColumnState,
                pagingList = pagingList,
                onClickDeleteButton = onClickDeleteButton,
                onClickMoveButton = onClickMoveButton,
                onClickAllItemMoveButton = onClickAllItemMoveButton,
            )
        }
    }
}
