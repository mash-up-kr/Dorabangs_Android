package com.dorabangs.feature.save

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DoraLinkSaveRoute(
    onClickBackIcon: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DoraLinkSaveScreen(
        modifier = modifier,
        onClickBackIcon = onClickBackIcon,
    )
}

@Composable
@Preview
fun DoraLinkSaveRouterPreview() {
    DoraLinkSaveRoute({})
}
