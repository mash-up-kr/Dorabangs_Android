package com.dorabangs.feature.save.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dorabangs.feature.save.DoraSaveSideEffect
import com.dorabangs.feature.save.DoraSaveViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun DoraLinkSaveSelectFolderRoute(
    onClickBackIcon: () -> Unit,
    finishSaveLink: () -> Unit,
    onClickAddNewFolder: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DoraSaveViewModel = hiltViewModel(),
) {
    val state by viewModel.collectAsState()
    if (state.isError) { onClickBackIcon.invoke() }
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is DoraSaveSideEffect.ClickItem -> {
                if (sideEffect.index == 0) {
                    onClickAddNewFolder(state.urlLink)
                } else {
                    viewModel.updateSelectedFolder(index = sideEffect.index)
                }
            }
            is DoraSaveSideEffect.ClickSaveButton -> viewModel.saveLink(id = sideEffect.id)
            is DoraSaveSideEffect.FinishSaveLink -> finishSaveLink()
        }
    }

    DoraLinkSaveSelectFolderScreen(
        modifier = modifier,
        state = state,
        onClickSaveButton = viewModel::clickSaveButton,
        onClickBackIcon = onClickBackIcon,
        onClickItem = viewModel::clickSelectableItem,
        getInitialFolder = viewModel::getFolderList,
    )
}
