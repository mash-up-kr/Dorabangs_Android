package com.mashup.feature.classification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.component.buttons.DoraButtons
import com.mashup.dorabangs.core.designsystem.component.card.FeedCard
import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel
import com.mashup.dorabangs.core.designsystem.component.snackbar.doraiconclose.CloseCircle
import com.mashup.dorabangs.core.designsystem.component.snackbar.doraiconclose.DoraIconClose
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens

@Composable
fun ClassificationListScreen(
    state: ClassificationState,
    modifier: Modifier = Modifier,
    onClickDeleteButton: (Int) -> Unit = {},
    onClickMoveButton: (Int) -> Unit = {},
    onClickAllItemMoveButton: () -> Unit = {},
) {
    LazyColumn(
        modifier = modifier.background(color = DoraColorTokens.White),
    ) {
        item {
            ClassificationFolderMove(
                selectedFolder = state.selectedFolder,
                onClickAllItemMoveButton = onClickAllItemMoveButton,
            )
            HorizontalDivider(thickness = 0.5.dp)
        }
        itemsIndexed(state.cardInfoList) { idx, item ->
            ClassificationCardItem(
                idx = idx,
                cardItem = item,
                cardItemList = state.cardInfoList,
                selectedFolder = state.selectedFolder,
                onClickDeleteButton = onClickDeleteButton,
                onClickMoveButton = onClickMoveButton,
            )
        }
    }
}

@Composable
fun ClassificationFolderMove(
    selectedFolder: String,
    modifier: Modifier = Modifier,
    count: Int = 0,
    onClickAllItemMoveButton: () -> Unit,
) {
    Column(
        modifier = modifier
            .background(color = DoraColorTokens.White)
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
    cardItem: FeedCardUiModel,
    cardItemList: List<FeedCardUiModel>,
    selectedFolder: String,
    modifier: Modifier = Modifier,
    onClickDeleteButton: (Int) -> Unit,
    onClickMoveButton: (Int) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = DoraColorTokens.White),
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        IconButton(
            modifier = Modifier
                .padding(end = 20.dp)
                .size(size = 24.dp)
                .align(Alignment.End),
            onClick = { onClickDeleteButton(idx) },
        ) {
            Image(
                imageVector = DoraIconClose.CloseCircle,
                contentDescription = "delete",
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        FeedCard(cardInfo = cardItem)
        DoraButtons.DoraColorFullMaxBtn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            buttonText = "$selectedFolder(으)로 옮기기",
            enabled = true,
            onClickButton = { onClickMoveButton(idx) },
        )
        Spacer(modifier = Modifier.height(32.dp))
        if (idx != cardItemList.lastIndex) {
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

@Preview
@Composable
fun PreviewClassificationFolderMove() {
    ClassificationListScreen(state = ClassificationState())
}
