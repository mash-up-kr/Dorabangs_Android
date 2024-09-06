package com.mashup.dorabangs.core.designsystem.component.snackbar

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.component.snackbar.doraiconarrow.DoraIconArrow
import com.mashup.dorabangs.core.designsystem.component.snackbar.doraiconarrow.RightArrow
import com.mashup.dorabangs.core.designsystem.theme.ClipBoardColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraRoundTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DoraSnackBar(
    text: String,
    onAction: (String) -> Unit,
    dismissAction: () -> Unit,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    SnackbarHost(
        modifier = modifier
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
            .clip(shape = DoraRoundTokens.Round12),
        hostState = snackBarHostState,
    ) {
        SnackBarContent(
            modifier = Modifier.clickable { onAction.invoke(text) },
            text = text,
            dismissAction = dismissAction,
        )
    }
}

@Composable
fun SnackBarContent(
    text: String,
    dismissAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(color = ClipBoardColorTokens.ContainerColor1)
            .padding(16.dp)
            .fillMaxWidth()
            .shadow(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(id = R.string.snack_bar_title),
                        maxLines = 2,
                        style = DoraTypoTokens.caption1Normal,
                        color = ClipBoardColorTokens.UrlLinkSubColor1,
                    )
                    Icon(
                        imageVector = DoraIconArrow.RightArrow,
                        tint = ClipBoardColorTokens.ArrowColor,
                        contentDescription = null,
                    )
                }
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = dismissAction,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_x_gray),
                        contentDescription = stringResource(id = R.string.snack_bar_cancel_description),
                    )
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = text,
                color = ClipBoardColorTokens.UrlLinkMainColor1,
                style = DoraTypoTokens.caption1Normal,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview
@Composable
fun SnackBarContentPreview() {
    SnackBarContent(
        text = "www.naver.com",
        dismissAction = {},
    )
}
