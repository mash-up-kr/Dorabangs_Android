package com.mashup.dorabangs.core.summary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
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
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.component.buttons.DoraButtons
import com.mashup.dorabangs.core.designsystem.component.card.FeedCardKeyword
import com.mashup.dorabangs.core.designsystem.component.topbar.DoraTopBar
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
import com.mashup.dorabangs.core.model.AISummaryUiModel
import com.mashup.dorabangs.core.webview.R as webViewR

@Composable
fun AISummaryRoute(
    aiSummaryUiModel: AISummaryUiModel,
    navigateToPopBackStack: () -> Unit,
) {
    AISummaryScreen(
        aiSummaryUiModel = aiSummaryUiModel,
        navigateToPopBackStack = navigateToPopBackStack,
    )
}

@Composable
fun AISummaryScreen(
    aiSummaryUiModel: AISummaryUiModel,
    navigateToPopBackStack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize().background(color = DoraColorTokens.White),
    ) {
        DoraTopBar.BackNavigationTopBar(
            modifier = Modifier.fillMaxWidth(),
            title = "",
            isTitleCenter = true,
            onClickBackIcon = navigateToPopBackStack,
            isShowBottomDivider = true,
        )
        Spacer(modifier = Modifier.height(30.dp))
        AISummaryContent(
            aiSummaryUiModel = aiSummaryUiModel,
            modifier = Modifier.padding(horizontal = 20.dp),
        )
        Spacer(modifier = Modifier.weight(1f))
        DoraButtons.DoraBtnMaxFull(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            enabled = true,
            buttonText = "닫기",
            onClickButton = navigateToPopBackStack,
        )
    }
}

@Composable
fun AISummaryContent(
    aiSummaryUiModel: AISummaryUiModel,
    modifier: Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = DoraColorTokens.White),
    ) {
        Row(
            modifier = Modifier,
        ) {
            Icon(
                modifier = Modifier.size(14.dp),
                tint = Color.Unspecified,
                painter = painterResource(id = webViewR.drawable.icon_summary_star),
                contentDescription = "summary_icon",
            )
            Text(
                text = stringResource(id = R.string.feed_card_content_title),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 6.dp),
                textAlign = TextAlign.Center,
                style = DoraTypoTokens.caption2Bold,
                color = DoraColorTokens.G9,
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = aiSummaryUiModel.description.orEmpty(),
            style = DoraTypoTokens.caption2Medium,
            color = DoraColorTokens.G7,
        )
        Spacer(modifier = Modifier.height(20.dp))
        FeedCardKeyword(keywordList = aiSummaryUiModel.keywords)
    }
}

@Preview
@Composable
fun PreviewAISummaryScreen() {
    AISummaryScreen(
        aiSummaryUiModel = AISummaryUiModel(),
        modifier = Modifier,
        navigateToPopBackStack = {},
    )
}
