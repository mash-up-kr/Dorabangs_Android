package com.mashup.dorabangs.core.summary

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
import com.mashup.dorabangs.core.webview.R

@Composable
fun AISummaryTopButton(
    modifier: Modifier,
    onClickSummary: () -> Unit,
) {
    Row(
        modifier = modifier
            .wrapContentSize()
            .clickable { onClickSummary() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_summary_star),
            tint = Color.Unspecified,
            contentDescription = "action",
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            style = DoraTypoTokens.caption1Medium,
            textAlign = TextAlign.Center,
            color = DoraColorTokens.Primary500,
            text = stringResource(id = R.string.webview_top_bar_summary),
        )
    }
}
