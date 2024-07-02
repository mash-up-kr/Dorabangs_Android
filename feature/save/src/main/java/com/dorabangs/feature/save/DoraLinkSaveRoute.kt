package com.dorabangs.feature.save

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DoraLinkSaveRoute(
    copiedUrl: String,
    onClickBackIcon: () -> Unit,
    onClickSaveButton: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DoraLinkSaveScreen(
        copiedUrl = copiedUrl,
        modifier = modifier,
        onClickBackIcon = onClickBackIcon,
        onClickSaveButton = onClickSaveButton,
    )
}

@Composable
@Preview
fun DoraLinkSaveRouterPreview() {
    DoraLinkSaveRoute(
        copiedUrl = "",
        onClickBackIcon = {},
        onClickSaveButton = {},
    )
}
