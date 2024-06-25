package com.mashup.dorabangs.core.designsystem.component.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens

@Composable
fun FeedCard(
    cardInfo: FeedCardUiModel,
    onClickCardItem: () -> Unit = {},
    onClickBookMarkButton: () -> Unit = {},
    onClickMoreButton: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .background(DoraColorTokens.P1, shape = RectangleShape)
            .fillMaxWidth()
            .padding(20.dp)
            .clickable { onClickCardItem() },
    ) {
        Row(
            modifier = Modifier.background(DoraColorTokens.P1),
        ) {
            FeedCardContent(
                modifier = Modifier.weight(3f),
                cardInfo = cardInfo,
            )
            Spacer(modifier = Modifier.width(13.dp))
            Image(
                modifier = Modifier.size(65.dp),
                painter = painterResource(id = cardInfo.thumbnail),
                contentDescription = "thumbnail",
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        FeedCardKeyword(keywordList = cardInfo.keywordList)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            FeedCardCategoryAndDayLabel(
                modifier = Modifier.align(Alignment.CenterVertically),
                cardInfo = cardInfo,
            )
            FeedCardMenuItems(
                onClickBookMarkButton = onClickBookMarkButton,
                onClickMoreButton = onClickMoreButton,
            )
        }
    }
}

@Composable
fun FeedCardContent(
    modifier: Modifier,
    cardInfo: FeedCardUiModel,
) {
    Column(
        modifier = Modifier.then(modifier),
    ) {
        Text(
            text = cardInfo.title,
            textAlign = TextAlign.Left,
            style = DoraTypoTokens.caption3Bold,
            color = DoraColorTokens.G9,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            Icon(
                modifier = Modifier.size(12.dp, 16.dp),
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = "",
            )
            Text(
                text = stringResource(id = R.string.feed_card_content_title),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 4.dp),
                textAlign = TextAlign.Center,
                style = DoraTypoTokens.SMedium,
                color = DoraColorTokens.G9,
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = cardInfo.content,
            textAlign = TextAlign.Left,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            style = DoraTypoTokens.caption1Normal,
            color = DoraColorTokens.G6,
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun FeedCardKeyword(keywordList: List<String>) {
    Row {
        keywordList.forEach { keyword ->
            Box(
                modifier = Modifier
                    .border(
                        width = 0.5.dp,
                        shape = RectangleShape,
                        color = DoraColorTokens.G3,
                    )
                    .background(color = DoraColorTokens.P1)
                    .padding(vertical = 6.dp, horizontal = 8.dp),
            ) {
                Text(
                    text = "# $keyword",
                    textAlign = TextAlign.Left,
                    style = DoraTypoTokens.XSBold,
                    color = DoraColorTokens.G7,
                    maxLines = 1,
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}

@Composable
fun FeedCardCategoryAndDayLabel(
    modifier: Modifier = Modifier,
    cardInfo: FeedCardUiModel,
) {
    Row(
        modifier = modifier.then(Modifier.wrapContentWidth()),
    ) {
        Text(
            text = cardInfo.category,
            style = DoraTypoTokens.XSNormal,
            color = DoraColorTokens.G5,
        )
        Icon(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(horizontal = 8.dp)
                .size(2.dp),
            painter = painterResource(id = R.drawable.ic_plus),
            contentDescription = "categoryLabel",
        )
        Text(
            text = "${cardInfo.createdAt}일 전",
            style = DoraTypoTokens.XSNormal,
            color = DoraColorTokens.G5,
        )
    }
}

@Composable
fun FeedCardMenuItems(
    onClickBookMarkButton: () -> Unit = {},
    onClickMoreButton: () -> Unit = {},
) {
    Row {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable { onClickBookMarkButton() },
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "menuIcon",
        )
        Spacer(modifier = Modifier.width(12.dp))
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable { onClickMoreButton() },
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "menuIcon",
        )
    }
}

@Preview
@Composable
private fun PreviewFeedCard() {
    val cardInfo =
        FeedCardUiModel(
            title = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
            content = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
            keywordList = listOf("다연", "호현", "석주"),
            category = "디자인",
            createdAt = 1,
            thumbnail = androidx.core.R.drawable.ic_call_answer,
        )
    FeedCard(cardInfo = cardInfo)
}
