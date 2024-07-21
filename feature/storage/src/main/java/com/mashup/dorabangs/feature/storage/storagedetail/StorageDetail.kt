package com.mashup.dorabangs.feature.storage.storagedetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailSort
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailState
import org.orbitmvi.orbit.compose.collectAsState

val MinToolbarHeight = 96.dp
val MaxToolbarHeight = 161.dp

@Composable
fun StorageDetailRoute(
    folderItem: Folder,
    storageDetailViewModel: StorageDetailViewModel = hiltViewModel(),
    onClickBackIcon: () -> Unit = {},
) {
    val listState = rememberLazyListState()
    val overlapHeightPx = with(LocalDensity.current) { MaxToolbarHeight.toPx() - MinToolbarHeight.toPx() }
    val state by storageDetailViewModel.collectAsState()
    val linksPagingList = state.pagingList.collectAsLazyPagingItems()

    LaunchedEffect(key1 = Unit) {
        storageDetailViewModel.setFolderInfo(folderItem)
    }

    val isCollapsed: Boolean by remember {
        derivedStateOf {
            val isFirstItemHidden = listState.firstVisibleItemScrollOffset > overlapHeightPx
            isFirstItemHidden || listState.firstVisibleItemIndex > 0
        }
    }

    StorageDetailScreen(
        state = state,
        linksPagingList = linksPagingList,
        listState = listState,
        isCollapsed = isCollapsed,
        onClickBackIcon = onClickBackIcon,
        onClickTabItem = storageDetailViewModel::changeSelectedTabIdx,
        onClickSortedIcon = storageDetailViewModel::clickFeedSort,
        onClickBookMarkButton = storageDetailViewModel::addFavoriteItem,
        onClickActionIcon = {},
    )
}

@Composable
fun StorageDetailScreen(
    linksPagingList: LazyPagingItems<FeedCardUiModel>,
    onClickBookMarkButton: (String, Boolean) -> Unit,
    onClickBackIcon: () -> Unit,
    onClickTabItem: (Int) -> Unit,
    onClickSortedIcon: (StorageDetailSort) -> Unit,
    onClickActionIcon: () -> Unit,
    modifier: Modifier = Modifier,
    state: StorageDetailState = StorageDetailState(),
    listState: LazyListState = rememberLazyListState(),
    isCollapsed: Boolean = false,
) {
    Box(
        modifier = modifier,
    ) {
        StorageDetailCollapsingHeader(
            state = state,
            modifier = Modifier.zIndex(2f),
            isCollapsed = isCollapsed,
            onClickBackIcon = onClickBackIcon,
            onClickTabItem = onClickTabItem,
            onClickActionIcon = onClickActionIcon,
        )
        StorageDetailList(
            listState = listState,
            linksPagingList = linksPagingList,
            state = state,
            onClickBackIcon = onClickBackIcon,
            onClickSortedIcon = onClickSortedIcon,
            onClickTabItem = onClickTabItem,
            onClickBookMarkButton = onClickBookMarkButton,

        )
    }
}

@Preview
@Composable
fun PreviewStorageDetailScreen() {
    StorageDetailRoute(
        folderItem = Folder(),
    )
}
