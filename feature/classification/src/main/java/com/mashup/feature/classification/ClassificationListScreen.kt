package com.mashup.feature.classification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.mashup.dorabangs.core.designsystem.component.buttons.DoraButtons
import com.mashup.dorabangs.core.designsystem.component.buttons.GradientButton
import com.mashup.dorabangs.core.designsystem.component.card.FeedCard
import com.mashup.dorabangs.core.designsystem.component.card.FeedCardEntryPoint
import com.mashup.dorabangs.core.designsystem.component.chips.FeedUiModel
import com.mashup.dorabangs.core.designsystem.component.snackbar.doraiconclose.CloseCircle
import com.mashup.dorabangs.core.designsystem.component.snackbar.doraiconclose.DoraIconClose
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraGradientToken
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens

@Composable
fun ClassificationListScreen(
    state: ClassificationState,
    lazyColumnState: LazyListState,
    pagingList: LazyPagingItems<FeedUiModel>,
    modifier: Modifier = Modifier,
    onClickCardItem: (String) -> Unit,
    onClickDeleteButton: (FeedUiModel.FeedCardUiModel) -> Unit = {},
    onClickMoveButton: (FeedUiModel.FeedCardUiModel) -> Unit = {},
    onClickAllItemMoveButton: () -> Unit = {},
) {
    LazyColumn(
        modifier = modifier.background(color = DoraColorTokens.White),
        state = lazyColumnState,
    ) {
        items(
            count = pagingList.itemCount,
            key = pagingList.itemKey(FeedUiModel::uuid),
            contentType = pagingList.itemContentType { "Feed Paging" },
        ) { idx ->
            pagingList[idx]?.let { item ->
                when (item) {
                    is FeedUiModel.DoraChipUiModel -> {
                        ClassificationFolderMove(
                            selectedFolder = item.title,
                            onClickAllItemMoveButton = onClickAllItemMoveButton,
                            count = item.postCount,
                        )
                    }

                    is FeedUiModel.FeedCardUiModel -> {
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
        Spacer(modifier = Modifier.height(20.dp))
        DoraButtons.DoraSmallConfirmBtn(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 30.dp, vertical = 8.dp)
                .wrapContentWidth(),
            buttonText = stringResource(id = R.string.ai_classification_all_move),
            onClickButton = { onClickAllItemMoveButton() },
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
    onClickCardItem: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = DoraColorTokens.White)
            .clickable { onClickCardItem(cardItem.url) },
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
                imageVector = DoraIconClose.CloseCircle,
                contentDescription = "delete",
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        FeedCard(cardInfo = cardItem, feedCardEntryPoint = FeedCardEntryPoint.AiClassification)
        GradientButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            gradientModifier = Modifier
                .fillMaxWidth()
                .height(36.dp),
            containerColor = DoraGradientToken.Gradient1,
            contentPadding = PaddingValues(horizontal = 20.dp),
            onClick = { onClickMoveButton(cardItem) },
        ) {
            Text(
                text = "${cardItem.category}(으)로 옮기기",
                modifier = Modifier.padding(vertical = 7.dp),
                style = DoraTypoTokens.caption1Medium.copy(
                    brush = DoraGradientToken.Gradient5,
                ),
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        if (idx != lastIndex) {
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                thickness = 0.5.dp,
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
