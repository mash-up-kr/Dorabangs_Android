package com.dorabangs.feature.save.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DoraLinkSaveRoute(
    onClickBackIcon: () -> Unit,
    onClickSaveButton: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DoraLinkSaveScreen(
        modifier = modifier,
        onClickBackIcon = onClickBackIcon,
        onClickSaveButton = onClickSaveButton,
    )
}

@Composable
@Preview
fun DoraLinkSaveRouterPreview() {
    DoraLinkSaveRoute(
        onClickBackIcon = {},
        onClickSaveButton = {},
    )
}
