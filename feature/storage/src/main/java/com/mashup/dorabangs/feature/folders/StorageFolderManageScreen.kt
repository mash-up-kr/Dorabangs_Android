package com.mashup.dorabangs.feature.folders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mashup.dorabangs.core.designsystem.component.buttons.DoraButtons
import com.mashup.dorabangs.core.designsystem.component.textfield.DoraTextField
import com.mashup.dorabangs.core.designsystem.component.topbar.DoraTopBar
import com.mashup.dorabangs.core.designsystem.theme.LinkSaveColorTokens
import com.mashup.dorabangs.feature.folders.model.FolderManageState
import com.mashup.dorabangs.feature.storage.R
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun StorageFolderManageRoute(
    folderManageType: String,
    onClickBackIcon: () -> Unit,
    folderManageViewModel: FolderManageViewModel = hiltViewModel()
) {
    val folderManageState by folderManageViewModel.collectAsState()

    LaunchedEffect(key1 = Unit) {
        folderManageViewModel.setFolderManageType(folderManageType)
    }

    FolderManageScreen(
        folderManageState = folderManageState,
        onClickBackIcon = onClickBackIcon,
        onClickSaveButton = { },
    )
}

@Composable
fun FolderManageScreen(
    folderManageState: FolderManageState,
    onClickBackIcon: () -> Unit,
    onClickSaveButton: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = LinkSaveColorTokens.ContainerColor),
    ) {
        DoraTopBar.BackNavigationTopBar(
            modifier = Modifier,
            title = stringResource(id = folderManageState.type.title),
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
                hintText = stringResource(id = R.string.storage_create_folder_hint),
                labelText = stringResource(id = R.string.storage_create_folder_label),
                helperText = stringResource(id = R.string.storage_create_folder_helper),
                helperEnabled = true,
                counterEnabled = true,
                onValueChanged = {},
            )
            Spacer(modifier = Modifier.height(20.dp))
            DoraButtons.DoraBtnMaxFull(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                buttonText = stringResource(id = R.string.storage_create_folder_save),
                enabled = true,
                onClickButton = onClickSaveButton,
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
