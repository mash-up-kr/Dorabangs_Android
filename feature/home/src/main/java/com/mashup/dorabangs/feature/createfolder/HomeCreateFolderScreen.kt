package com.mashup.dorabangs.feature.createfolder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mashup.dorabangs.core.designsystem.component.buttons.DoraButtons
import com.mashup.dorabangs.core.designsystem.component.textfield.DoraTextField
import com.mashup.dorabangs.core.designsystem.component.topbar.DoraTopBar
import com.mashup.dorabangs.core.designsystem.theme.LinkSaveColorTokens
import com.mashup.dorabangs.feature.home.HomeCreateFolder
import com.mashup.dorabangs.feature.home.HomeSideEffect
import com.mashup.dorabangs.feature.home.HomeViewModel
import com.mashup.dorabangs.home.R
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun HomeCreateFolderRoute(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onClickBackIcon: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToHomeAfterSaveLink: () -> Unit,
) {
    val state by homeViewModel.collectAsState()

    homeViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is HomeSideEffect.NavigateToHome -> navigateToHome()
            is HomeSideEffect.SaveLink -> homeViewModel.saveLink(
                folderId = sideEffect.folderId,
                urlLink = sideEffect.urlLink,
            )
            is HomeSideEffect.NavigateHomeAfterSaveLink -> navigateToHomeAfterSaveLink()
            else -> {}
        }
    }

    HomeCreateFolderScreen(
        state = state.homeCreateFolder,
        onClickBackIcon = onClickBackIcon,
        onClickSaveButton = { homeViewModel.createFolder(state.homeCreateFolder.folderName, state.homeCreateFolder.urlLink) },
        onValueChanged = homeViewModel::setFolderName,
    )
}

@Composable
fun HomeCreateFolderScreen(
    state: HomeCreateFolder,
    onClickBackIcon: () -> Unit,
    onClickSaveButton: () -> Unit,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = LinkSaveColorTokens.ContainerColor),
    ) {
        DoraTopBar.BackNavigationTopBar(
            modifier = Modifier,
            title = stringResource(id = R.string.home_create_folder_title),
            isTitleCenter = true,
            onClickBackIcon = onClickBackIcon,
        )
        Spacer(modifier = Modifier.height(height = 24.dp))
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
        ) {
            DoraTextField(
                text = "",
                hintText = stringResource(id = R.string.home_create_folder_hint),
                labelText = stringResource(id = R.string.home_create_folder_label),
                helperText = state.helperMessage,
                helperEnabled = state.helperEnable,
                counterEnabled = true,
                onValueChanged = onValueChanged,
            )
            Spacer(modifier = Modifier.height(20.dp))
            DoraButtons.DoraBtnMaxFull(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                buttonText = stringResource(id = R.string.home_create_folder_save),
                enabled = state.folderName.isNotEmpty() && !state.helperEnable,
                onClickButton = onClickSaveButton,
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
