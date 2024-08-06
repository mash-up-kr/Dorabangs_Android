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
import com.mashup.dorabangs.core.designsystem.component.chips.DoraChips
import com.mashup.dorabangs.core.designsystem.component.chips.FeedUiModel
import com.mashup.dorabangs.core.designsystem.component.topbar.DoraTopBar
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
        onClickCardItem = navigateToWebView,
    )
}

@Composable
fun ClassificationScreen(
    state: ClassificationState,
    pagingList: LazyPagingItems<FeedUiModel>,
    onClickChip: (Int) -> Unit,
    onClickDeleteButton: (FeedUiModel.FeedCardUiModel) -> Unit,
    onClickMoveButton: (FeedUiModel.FeedCardUiModel) -> Unit,
    onClickAllItemMoveButton: () -> Unit,
    onClickBackIcon: () -> Unit,
    navigateToHome: () -> Unit,
    onClickCardItem: (String) -> Unit,
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
            chipList = state.chipState.chipList.map {
                // 여기만 타이틀에서 연예 3 과 같이 숫자 들고 있어서 이렇게 변경함;
                it.copy(
                    title = it.mergedTitle,
                )
            },
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
            ClassificationListScreen(
                state = state,
                lazyColumnState = lazyColumnState,
                pagingList = pagingList,
                onClickDeleteButton = onClickDeleteButton,
                onClickMoveButton = onClickMoveButton,
                onClickAllItemMoveButton = onClickAllItemMoveButton,
                onClickCardItem = onClickCardItem,
            )
        }
    }
}
