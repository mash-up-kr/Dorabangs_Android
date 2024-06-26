package com.mashup.dorabangs.core.designsystem.component.snackbar

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.theme.ClipBoardColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraRoundTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DoraSnackBar(
    text: String,
    action: () -> Unit,
    dismissAction: () -> Unit,
    modifier: Modifier = Modifier,
    doraDuration: SnackbarDuration = SnackbarDuration.Short,
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = Unit) {
        scope.launch {
            snackBarHostState.showSnackbar(text, duration = doraDuration)
        }
    }

    SnackbarHost(
        modifier = modifier
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
            .clip(shape = DoraRoundTokens.Round12),
        hostState = snackBarHostState,
    ) {
        SnackBarContent(
            modifier = Modifier.clickable { action.invoke() },
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
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = "클립보드에 복사한 링크 저장",
                maxLines = 2,
                style = DoraTypoTokens.caption1Medium,
                color = ClipBoardColorTokens.UrlLinkSubColor1,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = text,
                color = ClipBoardColorTokens.UrlLinkMainColor1,
                style = DoraTypoTokens.caption3Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Spacer(
            modifier = Modifier.width(16.dp),
        )
        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = { dismissAction.invoke() },
        ) {
            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = "스낵바 취소",
                tint = ClipBoardColorTokens.UrlLinkSubColor1,
            )
        }
    }
}
