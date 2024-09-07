package com.mashup.feature.classification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.mashup.dorabangs.core.designsystem.component.buttons.DoraButtons
import com.mashup.dorabangs.core.designsystem.component.card.FeedCard
import com.mashup.dorabangs.core.designsystem.component.card.FeedCardEntryPoint
import com.mashup.dorabangs.core.designsystem.component.chips.FeedUiModel
import com.mashup.dorabangs.core.designsystem.component.divider.DoraDivider
import com.mashup.dorabangs.core.designsystem.component.snackbar.DoraCloseButton
import com.mashup.dorabangs.core.designsystem.component.snackbar.doraiconclosev2.IC_CLOSE_BUTTON
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraGradientToken
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens

@Composable
fun ClassificationListScreen(
    state: ClassificationState,
    lazyColumnState: LazyListState,
    pagingList: LazyPagingItems<FeedUiModel>,
    modifier: Modifier = Modifier,
    onClickCardItem: (FeedUiModel.FeedCardUiModel) -> Unit,
    onClickDeleteButton: (FeedUiModel.FeedCardUiModel) -> Unit = {},
    onClickMoveButton: (FeedUiModel.FeedCardUiModel) -> Unit = {},
    onClickAllItemMoveButton: (String) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier.background(color = DoraColorTokens.White),
        state = lazyColumnState,
    ) {
        items(
            count = pagingList.itemCount,
            key = pagingList.itemKey(FeedUiModel::uuid),
            contentType = { if (pagingList[it] is FeedUiModel.FeedCardUiModel) "feed_card" else "header" },
        ) { idx ->
            pagingList[idx]?.let { item ->
                when (item) {
                    is FeedUiModel.DoraChipUiModel -> {
                        if (state.selectedFolder == item.title || state.selectedFolder == "전체") {
                            ClassificationFolderMove(
                                selectedFolder = item.title,
                                onClickAllItemMoveButton = { onClickAllItemMoveButton(item.folderId) },
                                count = item.postCount,
                            )
                        }
                    }

                    is FeedUiModel.FeedCardUiModel -> {
                        if (state.selectedFolder == item.category || state.selectedFolder == "전체") {
                            ClassificationCardItem(
                                idx = idx,
                                lastIndex = pagingList.itemCount - 1,
                                cardItem = item,
                                onClickDeleteButton = onClickDeleteButton,
                                onClickMoveButton = onClickMoveButton,
                                onClickCardItem = onClickCardItem,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ClassificationFolderMove(
    selectedFolder: String,
    onClickAllItemMoveButton: () -> Unit,
    modifier: Modifier = Modifier,
    count: Int = 0,
) {
    Column(
        modifier = modifier
            .background(DoraGradientToken.Gradient2)
            .fillMaxWidth()
            .padding(vertical = 32.dp, horizontal = 23.dp),
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = selectedFolder,
            style = DoraTypoTokens.TitleBold,
            color = DoraColorTokens.Black,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "분류한 링크 ${count}개",
            style = DoraTypoTokens.caption1Medium,
            color = DoraColorTokens.Black,
        )
        Spacer(modifier = Modifier.height(12.dp))
        DoraButtons.DoraSmallConfirmBtn(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 24.dp, vertical = 6.dp)
                .height(36.dp),
            buttonText = stringResource(id = R.string.ai_classification_all_move),
            onClickButton = onClickAllItemMoveButton,
        )
    }
}

@Composable
fun ClassificationCardItem(
    idx: Int,
    lastIndex: Int,
    cardItem: FeedUiModel.FeedCardUiModel,
    onClickDeleteButton: (FeedUiModel.FeedCardUiModel) -> Unit,
    onClickMoveButton: (FeedUiModel.FeedCardUiModel) -> Unit,
    onClickCardItem: (FeedUiModel.FeedCardUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = DoraColorTokens.White)
            .clickable { onClickCardItem(cardItem) },
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        IconButton(
            modifier = Modifier
                .padding(end = 20.dp)
                .size(size = 24.dp)
                .align(Alignment.End),
            onClick = { onClickDeleteButton(cardItem) },
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                imageVector = DoraCloseButton.IC_CLOSE_BUTTON,
                contentDescription = "delete",
            )
        }
        FeedCard(cardInfo = cardItem, feedCardEntryPoint = FeedCardEntryPoint.AiClassification)
        DoraButtons.DoraBtnMaxFullWithTextStyle(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            containerColor = DoraColorTokens.Primary100,
            buttonText = "${cardItem.category}(으)로 옮기기",
            textStyle = DoraTypoTokens.caption1Medium.copy(
                color = DoraColorTokens.Primary500,
            ),
            enabled = true,
            onClickButton = { onClickMoveButton(cardItem) },
        )
        Spacer(modifier = Modifier.height(32.dp))
        if (idx != lastIndex) {
            DoraDivider(modifier = Modifier.padding(horizontal = 20.dp))
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
