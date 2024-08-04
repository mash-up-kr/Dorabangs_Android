package com.mashup.dorabangs.feature.storage.storagedetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.mashup.dorabangs.core.designsystem.component.card.FeedCard
import com.mashup.dorabangs.core.designsystem.component.card.FeedCardEntryPoint
import com.mashup.dorabangs.core.designsystem.component.util.LottieLoader
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailSort
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailState
import com.mashup.dorabangs.core.designsystem.R as coreR
import com.mashup.dorabangs.core.designsystem.component.chips.FeedUiModel

@Composable
fun StorageDetailList(
    listState: LazyListState,
    linksPagingList: LazyPagingItems<FeedUiModel.FeedCardUiModel>,
    state: StorageDetailState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    onClickBackIcon: () -> Unit,
    onClickTabItem: (Int) -> Unit,
    onClickActionIcon: () -> Unit,
    onClickMoreButton: (String) -> Unit,
    onClickPostItem: (String) -> Unit,
    onClickBookMarkButton: (String, Boolean) -> Unit,
    onClickSortedIcon: (StorageDetailSort) -> Unit = {},
) {
    val isLoading = linksPagingList.loadState.refresh is LoadState.Loading
    if (linksPagingList.itemCount == 0) {
        Box {
            Column {
                StorageDetailExpandedHeader(
                    state = state,
                    onClickBackIcon = onClickBackIcon,
                    onClickTabItem = onClickTabItem,
                    onClickActionIcon = onClickActionIcon,
                )
                if (!isLoading) {
                    StorageDetailEmpty(modifier = modifier)
                }
            }
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                ) {
                    LottieLoader(
                        lottieRes = coreR.raw.spinner,
                        modifier = Modifier
                            .size(54.dp)
                            .align(Alignment.Center),
                    )
                }
            }
        }
    } else {
        Box {
            LazyColumn(
                state = listState,
                contentPadding = contentPadding,
                modifier = modifier,
            ) {
                item {
                    StorageDetailExpandedHeader(
                        state = state,
                        onClickBackIcon = onClickBackIcon,
                        onClickTabItem = onClickTabItem,
                        onClickActionIcon = onClickActionIcon,
                    )
                }
                if (!isLoading) {
                    item {
                        SortButtonRow(
                            isLatestSort = state.isLatestSort == StorageDetailSort.ASC,
                            onClickSortedIcon = onClickSortedIcon,
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                    items(
                        count = linksPagingList.itemCount,
                        key = linksPagingList.itemKey { item ->
                            "${item.postId}_${item.isFavorite}"
                        },
                        contentType = linksPagingList.itemContentType { "SavedLinks" },
                    ) { idx ->
                        linksPagingList[idx]?.let { cardItem ->
                            val item = cardItem.copy(category = state.folderInfo.title)
                            FeedCard(
                                cardInfo = item,
                                onClickCardItem = onClickPostItem,
                                onClickBookMarkButton = { onClickBookMarkButton(cardItem.postId, cardItem.isFavorite) },
                                onClickMoreButton = { onClickMoreButton(cardItem.postId) },
                                feedCardEntryPoint = FeedCardEntryPoint.StorageDetail,
                            )
                            if (idx != state.folderInfo.postCount - 1) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 20.dp)
                                        .height(0.5.dp)
                                        .background(DoraColorTokens.G2),
                                )
                            }
                        }
                    }
                }
            }
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    LottieLoader(
                        lottieRes = coreR.raw.spinner,
                        modifier = Modifier
                            .size(54.dp)
                            .align(Alignment.Center),
                    )
                }
            }
        }
    }
}

@Composable
fun SortButtonRow(
    items: List<StorageDetailSort> = listOf(StorageDetailSort.ASC, StorageDetailSort.DESC),
    isLatestSort: Boolean = false,
    onClickSortedIcon: (StorageDetailSort) -> Unit = {},
) {
    Row(
        modifier =
        Modifier
            .fillMaxWidth()
            .background(color = DoraColorTokens.White)
            .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 14.dp),
        horizontalArrangement = Arrangement.End,
    ) {
        items.forEachIndexed { index, item ->
            Row(
                modifier =
                Modifier
                    .align(Alignment.CenterVertically)
                    .background(color = DoraColorTokens.White)
                    .clickable { onClickSortedIcon(item) },
            ) {
                val icon = when (item) {
                    StorageDetailSort.ASC -> if (isLatestSort) coreR.drawable.ic_arrow_down_active else coreR.drawable.ic_arrow_down_disabled
                    StorageDetailSort.DESC -> if (isLatestSort) coreR.drawable.ic_arrow_up_disabled else coreR.drawable.ic_arrow_up_active
                }
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = icon),
                    contentDescription = stringResource(id = item.btnName),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = stringResource(id = item.btnName),
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    style = DoraTypoTokens.caption1Medium,
                )
            }
            if (index != items.lastIndex) Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Preview
@Composable
fun PreviewSortRowButton() {
    SortButtonRow()
}
