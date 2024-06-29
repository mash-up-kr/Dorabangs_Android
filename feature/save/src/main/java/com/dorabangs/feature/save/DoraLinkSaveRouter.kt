package com.dorabangs.feature.save

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.component.topbar.DoraTopBar
import com.mashup.dorabangs.core.designsystem.theme.LinkSaveColorTokens

@Composable
fun DoraLinkSaveRouter(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = LinkSaveColorTokens.ContainerColor),
    ) {
        DoraTopBar.BackNavigationTopBar(
            modifier = Modifier,
            title = "링크저장",
            titleAlignment = Alignment.Center,
            onClickBackIcon = {}
        )
        Spacer(modifier = Modifier.height(height = 24.dp))
        DoraLinkSaveScreen()
    }
}

@Composable
@Preview
fun DoraLinkSaveRouterPreview() {
    DoraLinkSaveRouter()
}