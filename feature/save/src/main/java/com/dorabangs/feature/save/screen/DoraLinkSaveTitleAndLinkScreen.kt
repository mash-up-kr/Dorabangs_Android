package com.dorabangs.feature.save.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
            .padding(all = 20.dp)
            .height(88.dp)
            .clip(DoraRoundTokens.Round12),
    ) {
        AsyncImage(
            modifier = Modifier.size(size = 88.dp),
            model = ImageRequest.Builder(context)
                .data(state.thumbnailUrl)
                .crossfade(false)
                .build(),
            contentDescription = "url 썸네일",
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = LinkSaveColorTokens.LinkContainerBackgroundColor)
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = state.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                style = DoraTypoTokens.caption3Bold,
                color = LinkSaveColorTokens.TitleTextColor,
            )

            Spacer(
                modifier = Modifier.height(height = 4.dp),
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = state.urlLink,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = DoraTypoTokens.SMedium,
                color = LinkSaveColorTokens.LinkTextColor,
            )
        }
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
        )
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
        )
    )
}
