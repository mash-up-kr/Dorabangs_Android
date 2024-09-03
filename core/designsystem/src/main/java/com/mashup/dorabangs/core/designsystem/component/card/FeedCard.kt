package com.mashup.dorabangs.core.designsystem.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.draw.clip
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
import com.mashup.dorabangs.core.designsystem.component.chips.FeedUiModel
import com.mashup.dorabangs.core.designsystem.component.chips.FeedUiModel.FeedCardUiModel.Companion.convertCreatedDate
import com.mashup.dorabangs.core.designsystem.component.chips.FeedUiModel.FeedCardUiModel.Companion.convertCreatedSecond
import com.mashup.dorabangs.core.designsystem.component.util.thenIf
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraRoundTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
import kotlinx.coroutines.delay

@Composable
fun FeedCard(
    cardInfo: FeedUiModel.FeedCardUiModel,
    feedCardEntryPoint: FeedCardEntryPoint,
    modifier: Modifier = Modifier,
    onClickCardItem: (FeedUiModel.FeedCardUiModel) -> Unit = {},
    onClickBookMarkButton: () -> Unit = {},
    onClickMoreButton: () -> Unit = {},
    requestUpdate: (String) -> Unit = {},
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
                8000
            } else {
                requested = true
                loadingSecond * 1000L
            }
            delay(currentLoadingSecond)
            requestUpdate.invoke(cardInfo.postId)
        }
    }
    Column(
        modifier = modifier
            .background(DoraColorTokens.P1, shape = RectangleShape)
            .fillMaxWidth()
            .thenIf(feedCardEntryPoint !is FeedCardEntryPoint.AiClassification) {
                this.clickable { onClickCardItem(cardInfo) }
            }
            .padding(20.dp),
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
                    .size(size = 80.dp)
                    .aspectRatio(1f)
                    .clip(DoraRoundTokens.Round4)
                    .background(color = DoraColorTokens.G1),
                model = cardInfo.thumbnail,
                error = painterResource(id = R.drawable.default_thumbnail),
                contentScale = ContentScale.Crop,
                contentDescription = "url 썸네일",
            )
        }
        if (cardInfo.isLoading) {
            FeedCardCategoryAndDayLabel(
                cardInfo = cardInfo,
            )
        } else {
            Spacer(modifier = Modifier.height(12.dp))
            FeedCardKeyword(keywordList = cardInfo.keywordList?.filterNotNull()?.take(3))
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
                    feedCardEntryPoint = feedCardEntryPoint,
                )
            }
        }
    }
}

@Composable
fun FeedCardContent(
    cardInfo: FeedUiModel.FeedCardUiModel,
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
                        .padding(start = 2.dp),
                    textAlign = TextAlign.Center,
                    style = DoraTypoTokens.SMedium,
                    color = DoraColorTokens.G9,
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            repeat(3) {
                TextLoadingSkeleton(
                    primaryColor = DoraColorTokens.White,
                    containerColor = DoraColorTokens.Primary500,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .height(14.dp),
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextLoadingSkeleton(
                primaryColor = DoraColorTokens.White,
                containerColor = DoraColorTokens.Primary500,
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
                        .padding(start = 2.dp),
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
                style = DoraTypoTokens.SNormal,
                color = DoraColorTokens.G6,
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun FeedCardKeyword(
    keywordList: List<String?>?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        keywordList?.forEach { keyword ->
            Box(
                modifier = Modifier
                    .height(26.dp)
                    .border(
                        width = 1.dp,
                        shape = RectangleShape,
                        color = DoraColorTokens.G3,
                    )
                    .background(color = DoraColorTokens.P1)
                    .padding(vertical = 6.dp, horizontal = 8.dp),
                contentAlignment = Alignment.Center,
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
    cardInfo: FeedUiModel.FeedCardUiModel,
) {
    Row(
        modifier = modifier.then(Modifier.wrapContentWidth()),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = cardInfo.category.orEmpty(),
            style = DoraTypoTokens.XSNormal,
            color = DoraColorTokens.G6,
        )
        cardInfo.createdAt?.let { day ->
            if (day.isNotEmpty()) {
                val days = day.convertCreatedDate()
                Text(
                    text = if (days == 0L) "오늘" else "${day.convertCreatedDate()}일 전",
                    style = DoraTypoTokens.XSNormal,
                    color = DoraColorTokens.G4,
                )
            }
        }
    }
}

@Composable
fun FeedCardMenuItems(
    cardInfo: FeedUiModel.FeedCardUiModel,
    feedCardEntryPoint: FeedCardEntryPoint,
    onClickBookMarkButton: () -> Unit = {},
    onClickMoreButton: () -> Unit = {},
) {
    if (feedCardEntryPoint !is FeedCardEntryPoint.AiClassification) {
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
}

sealed class FeedCardEntryPoint {
    object StorageDetail : FeedCardEntryPoint()
    object AiClassification : FeedCardEntryPoint()
    object Home : FeedCardEntryPoint()
}

@Preview
@Composable
private fun PreviewFeedCardWithEmptyUrl() {
    val cardInfo =
        FeedUiModel.FeedCardUiModel(
            postId = "",
            title = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
            content = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
            keywordList = listOf("다연", "호현", "석주"),
            category = "디자인",
            createdAt = "2024-07-18T15:50:36.181Z",
            thumbnail = "https://www.naver.com",
            folderId = "",
        )
    FeedCard(cardInfo = cardInfo, feedCardEntryPoint = FeedCardEntryPoint.StorageDetail)
}

@Preview
@Composable
private fun PreviewFeedCard() {
    val cardInfo =
        FeedUiModel.FeedCardUiModel(
            postId = "",
            title = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
            content = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
            keywordList = listOf("다연", "호현", "석주"),
            category = "디자인",
            createdAt = "2024-07-18T15:50:36.181Z",
            thumbnail = "",
            folderId = "",
        )
    FeedCard(cardInfo = cardInfo, feedCardEntryPoint = FeedCardEntryPoint.StorageDetail)
}

@Preview
@Composable
private fun PreviewLoadingFeedCard() {
    val cardInfo =
        FeedUiModel.FeedCardUiModel(
            postId = "",
            title = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
            content = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
            keywordList = listOf("다연", "호현", "석주"),
            category = "디자인",
            createdAt = "2024-07-18T15:50:36.181Z",
            thumbnail = "",
            isLoading = true,
            folderId = "",
        )
    FeedCard(cardInfo = cardInfo, feedCardEntryPoint = FeedCardEntryPoint.AiClassification)
}

private fun Int.between(min: Int, max: Int): Int {
    if (this < min) return min
    if (this > max) return max
    return this
}
