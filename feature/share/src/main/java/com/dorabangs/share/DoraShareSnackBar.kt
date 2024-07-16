package com.dorabangs.share

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraRoundTokens

@Composable
fun DoraShareSnackBar(
    snackBarHostState: SnackbarHostState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SnackbarHost(
        modifier = modifier,
        hostState = snackBarHostState,
    ) {
        Row(
            modifier = Modifier
                .clip(DoraRoundTokens.Round8)
                .background(DoraColorTokens.G9)
                .padding(horizontal = 18.dp, vertical = 16.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "나중에 읽을 링크에 저장했어요!",
                    color = DoraColorTokens.White,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    VerticalDivider(
                        thickness = 1.dp,
                    )
                }
            }

            Text(
                modifier = Modifier.clickable { onClick() },
                textAlign = TextAlign.Center,
                text = "편집",
                color = DoraColorTokens.White,
            )
        }
    }
}

@Preview
@Composable
fun DoraShareSnackBarPreview() {
    DoraShareSnackBar(
        onClick = { /*TODO*/ },
        snackBarHostState = SnackbarHostState(),
    )
}
