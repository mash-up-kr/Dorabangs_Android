package com.dorabangs.feature.save.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.dorabangs.feature.save.DoraSaveViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun DoraLinkSaveRoute(
    onClickBackIcon: () -> Unit,
    onClickSaveButton: (String) -> Unit,
    modifier: Modifier = Modifier,
    saveViewModel: DoraSaveViewModel = hiltViewModel(),
) {
    val state by saveViewModel.collectAsState()

    DoraLinkSaveScreen(
        modifier = modifier,
        state = state,
        onClickBackIcon = onClickBackIcon,
        onClickSaveButton = { onClickSaveButton(state.urlLink) },
        onValueChanged = { url -> saveViewModel.checkUrl(urlLink = url) },
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
