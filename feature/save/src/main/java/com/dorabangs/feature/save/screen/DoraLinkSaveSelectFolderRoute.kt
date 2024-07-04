package com.dorabangs.feature.save.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DoraLinkSaveSelectFolderRoute(
    onClickBackIcon: () -> Unit,
    onClickSaveButton: () -> Unit,
    modifier: Modifier = Modifier,
    url: String = "",
) {
    DoraLinkSaveSelectFolderScreen(
        modifier = modifier,
        onClickSaveButton = onClickSaveButton,
        onClickBackIcon = onClickBackIcon,
        url = url,
    )
}
