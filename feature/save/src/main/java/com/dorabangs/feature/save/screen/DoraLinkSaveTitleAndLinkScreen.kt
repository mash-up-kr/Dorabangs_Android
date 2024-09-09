package com.dorabangs.feature.save.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dorabangs.feature.save.DoraSaveState
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.theme.DoraRoundTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
import com.mashup.dorabangs.core.designsystem.theme.LinkSaveColorTokens

@Composable
fun DoraLinkSaveTitleAndLinkScreen(
    state: DoraSaveState,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
) {
    Row(
        modifier = modifier
            .background(color = LinkSaveColorTokens.LinkContainerBackgroundColor)
            .height(120.dp)
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = state.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                style = DoraTypoTokens.caption3Bold,
                color = LinkSaveColorTokens.TitleTextColor,
            )

            Text(
                text = state.urlLink,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = DoraTypoTokens.SNormal,
                color = LinkSaveColorTokens.LinkTextColor,
            )
        }
        AsyncImage(
            modifier = Modifier
                .size(size = 80.dp)
                .clip(DoraRoundTokens.Round4),
            model = ImageRequest.Builder(context)
                .data(state.thumbnailUrl)
                .crossfade(false)
                .build(),
            error = painterResource(id = R.drawable.default_thumbnail),
            contentDescription = "url 썸네일",
        )
    }
}

@Composable
@Preview
fun DoraLinkSaveTitleAndLinkScreenPreview() {
    DoraLinkSaveTitleAndLinkScreen(
        state = DoraSaveState(
            urlLink = "https://www.naver.com/articale",
            thumbnailUrl = "https://www.naver.com/articale",
            title = "넌 바보",
            isShortLink = false,
        ),
    )
}

@Composable
@Preview
fun DoraLinkSaveTitleAndLinkScreenPreviewShort() {
    DoraLinkSaveTitleAndLinkScreen(
        state = DoraSaveState(
            urlLink = "https://www.naver.com/articale",
            thumbnailUrl = "https://www.naver.com/articale",
            title = "넌 바보",
            isShortLink = false,
        ),
    )
}
