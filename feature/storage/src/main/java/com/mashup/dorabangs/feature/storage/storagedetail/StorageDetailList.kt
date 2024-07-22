package com.mashup.dorabangs.feature.storage.storagedetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
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
import com.mashup.dorabangs.core.designsystem.component.card.FeedCard
import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailSort
import com.mashup.dorabangs.feature.storage.storagedetail.model.StorageDetailState
import com.mashup.dorabangs.core.designsystem.R as coreR

@Composable
fun StorageDetailList(
    listState: LazyListState,
    state: StorageDetailState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    onClickBackIcon: () -> Unit = {},
    onClickSortedIcon: (StorageDetailSort) -> Unit = {},
) {
    LazyColumn(
        state = listState,
        contentPadding = contentPadding,
        modifier = modifier,
    ) {
        item {
            StorageDetailExpandedHeader(
                state = state,
                onClickBackIcon = onClickBackIcon,
            )
        }
        item {
            SortButtonRow(
                isLatestSort = state.isLatestSort,
                onClickSortedIcon = onClickSortedIcon,
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        itemsIndexed(FeedCardUiModel.getDefaultFeedCard()) { idx, cardInfo ->
            FeedCard(cardInfo = cardInfo)
            if (idx != FeedCardUiModel.getDefaultFeedCard().lastIndex) {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 24.dp, horizontal = 20.dp),
                    thickness = 0.5.dp,
                )
            }
        }
    }
}

@Composable
fun SortButtonRow(
    isLatestSort: Boolean = false,
    items: List<StorageDetailSort> = listOf(StorageDetailSort.LATEST, StorageDetailSort.PAST),
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
                    StorageDetailSort.LATEST -> if (isLatestSort) coreR.drawable.ic_arrow_down_active else coreR.drawable.ic_arrow_down_disabled
                    StorageDetailSort.PAST -> if (isLatestSort) coreR.drawable.ic_arrow_up_disabled else coreR.drawable.ic_arrow_up_active
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
