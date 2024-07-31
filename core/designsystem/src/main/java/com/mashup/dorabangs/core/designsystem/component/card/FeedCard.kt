package com.mashup.dorabangs.core.designsystem.component.card

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel.Companion.convertCreatedDate
import com.mashup.dorabangs.core.designsystem.component.card.FeedCardUiModel.Companion.convertCreatedSecond
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraGradientToken
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
import kotlinx.coroutines.delay

@Composable
fun FeedCard(
    cardInfo: FeedCardUiModel,
    modifier: Modifier = Modifier,
    onClickCardItem: () -> Unit = {},
    onClickBookMarkButton: () -> Unit = {},
    onClickMoreButton: () -> Unit = {},
    updateCardState: () -> Unit = {},
) {
    val loadingSecond = if (!cardInfo.createdAt.isNullOrBlank()) {
        cardInfo.createdAt.convertCreatedSecond()
    } else {
        8
    }.between(0, 8)

    LaunchedEffect(cardInfo.isLoading) {
        var requested = false
        while (cardInfo.isLoading) {
            val currentLoadingSecond = if (requested) {
                4000
            } else {
                requested = true
                loadingSecond * 1000L
            }
            delay(currentLoadingSecond)
            updateCardState()
        }
    }

    Column(
        modifier = modifier
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
                isLoading = cardInfo.isLoading,
            )
            Spacer(modifier = Modifier.width(13.dp))
            AsyncImage(
                modifier = Modifier
                    .size(size = 65.dp)
                    .background(color = DoraColorTokens.G1),
                model = cardInfo.thumbnail,
                contentScale = ContentScale.Crop,
                contentDescription = "url 썸네일",
            )
        }
        if (cardInfo.isLoading) {
            CardProgressBar(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .height(4.dp),
                completedColor = DoraColorTokens.Primary,
                remainColor = DoraGradientToken.Gradient2,
                timeInProgress = minOf(0.8f, cardInfo.createdAt.convertCreatedSecond() / 8f),
            )
            FeedCardCategoryAndDayLabel(
                cardInfo = cardInfo,
            )
        } else {
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
                    cardInfo = cardInfo,
                    onClickBookMarkButton = onClickBookMarkButton,
                    onClickMoreButton = onClickMoreButton,
                )
            }
        }
    }
}

@Composable
fun FeedCardContent(
    cardInfo: FeedCardUiModel,
    modifier: Modifier,
    isLoading: Boolean,
) {
    Column(
        modifier = Modifier.then(modifier),
    ) {
        Text(
            text = cardInfo.title.orEmpty(),
            textAlign = TextAlign.Left,
            style = DoraTypoTokens.caption3Bold,
            color = DoraColorTokens.G9,
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (isLoading) {
            Row {
                Icon(
                    modifier = Modifier.size(12.dp, 16.dp),
                    painter = painterResource(id = R.drawable.ic_ai_14dp),
                    contentDescription = "",
                )
                Text(
                    text = stringResource(id = R.string.feed_card_summarizing_content__title),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 4.dp),
                    textAlign = TextAlign.Center,
                    style = DoraTypoTokens.SMedium,
                    color = DoraColorTokens.G9,
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            repeat(3) {
                TextLoadingSkeleton(
                    primaryColor = DoraColorTokens.White,
                    containerColor = DoraColorTokens.Primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .height(14.dp),
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextLoadingSkeleton(
                primaryColor = DoraColorTokens.White,
                containerColor = DoraColorTokens.Primary,
                modifier = Modifier
                    .padding(vertical = 2.dp)
                    .width(172.dp)
                    .height(14.dp),
            )
        } else {
            Row {
                Icon(
                    modifier = Modifier.size(14.dp),
                    painter = painterResource(id = R.drawable.ic_ai_14dp),
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
                text = cardInfo.content.orEmpty(),
                textAlign = TextAlign.Left,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = DoraTypoTokens.caption1Normal,
                color = DoraColorTokens.G6,
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun FeedCardKeyword(keywordList: List<String?>?) {
    Row {
        keywordList?.forEach { keyword ->
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
            text = cardInfo.category.orEmpty(),
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
        cardInfo.createdAt?.let { day ->
            if (day.isNotEmpty()) {
                Text(
                    text = "${day.convertCreatedDate()}일 전",
                    style = DoraTypoTokens.XSNormal,
                    color = DoraColorTokens.G5,
                )
            }
        }
    }
}

@Composable
fun FeedCardMenuItems(
    cardInfo: FeedCardUiModel,
    onClickBookMarkButton: () -> Unit = {},
    onClickMoreButton: () -> Unit = {},
) {
    Row {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable { onClickBookMarkButton() },
            painter = if (cardInfo.isFavorite) {
                painterResource(id = R.drawable.ic_bookmark_active)
            } else {
                painterResource(
                    id = R.drawable.ic_bookmark_default,
                )
            },
            contentDescription = "menuIcon",
            tint = Color.Unspecified,
        )
        Spacer(modifier = Modifier.width(12.dp))
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable { onClickMoreButton() },
            painter = painterResource(id = R.drawable.ic_more_gray),
            contentDescription = "menuIcon",
            tint = Color.Unspecified,
        )
    }
}

@Preview
@Composable
private fun PreviewFeedCard() {
    val cardInfo =
        FeedCardUiModel(
            id = "",
            title = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
            content = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
            keywordList = listOf("다연", "호현", "석주"),
            category = "디자인",
            createdAt = "2024-07-18T15:50:36.181Z",
            thumbnail = "",
            folderId = "",
        )
    FeedCard(cardInfo = cardInfo)
}

@Preview
@Composable
private fun PreviewLoadingFeedCard() {
    val cardInfo =
        FeedCardUiModel(
            id = "",
            title = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
            content = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
            keywordList = listOf("다연", "호현", "석주"),
            category = "디자인",
            createdAt = "2024-07-18T15:50:36.181Z",
            thumbnail = "",
            isLoading = true,
            folderId = "",
        )
    FeedCard(cardInfo = cardInfo)
}

private fun Int.between(min: Int, max: Int): Int {
    if (this < min) return min
    if (this > max) return max
    return this
}
