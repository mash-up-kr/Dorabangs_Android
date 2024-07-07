package com.dorabangs.feature.save.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dorabangs.feature.save.DoraSaveViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun DoraLinkSaveSelectFolderRoute(
    onClickBackIcon: () -> Unit,
    onClickSaveButton: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DoraSaveViewModel = hiltViewModel(),
) {
    val state by viewModel.collectAsState()

    DoraLinkSaveSelectFolderScreen(
        modifier = modifier,
        state = state,
        onClickSaveButton = onClickSaveButton,
        onClickBackIcon = onClickBackIcon,
    )
}
