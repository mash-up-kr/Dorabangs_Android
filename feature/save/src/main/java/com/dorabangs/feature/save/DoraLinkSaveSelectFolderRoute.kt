package com.dorabangs.feature.save

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DoraLinkSaveSelectFolderRoute(
    onClickBackIcon: () -> Unit,
    onClickSaveButton: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DoraLinkSaveSelectFolderScreen(
        modifier = modifier,
        onClickSaveButton = onClickSaveButton,
        onClickBackIcon = onClickBackIcon,
    )
}
