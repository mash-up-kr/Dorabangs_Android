package com.mashup.dorabangs.feature.storage.storage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mashup.dorabangs.core.designsystem.component.bottomsheet.DoraBottomSheet
import com.mashup.dorabangs.core.designsystem.component.dialog.DoraDialog
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
import com.mashup.dorabangs.feature.folders.model.FolderManageType
import com.mashup.dorabangs.feature.storage.R
import com.mashup.dorabangs.feature.storage.storage.model.StorageFolderItem
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun StorageRoute(
    storageViewModel: StorageViewModel = hiltViewModel(),
    navigateToStorageDetail: (StorageFolderItem) -> Unit = {},
    navigateToFolderManage: (FolderManageType) -> Unit = {}
) {
    val storageState by storageViewModel.collectAsState()
    Box {
        StorageScreen(
            navigateToStorageDetail = navigateToStorageDetail,
            onClickSettingButton = { storageViewModel.setVisibleMoreButtonBottomSheet(visible = true) },
            onClickAddFolderIcon = { navigateToFolderManage(FolderManageType.CREATE) },
        )
        DoraBottomSheet.MoreButtonBottomSheet(
            modifier = Modifier.height(320.dp),
            isShowSheet = storageState.isShowMoreButtonSheet,
            firstItemName = R.string.storage_more_bottom_sheet_folder_remove ,
            secondItemName = R.string.storage_more_bottom_sheet_folder_name_change,
            onClickDeleteLinkButton = {
                storageViewModel.setVisibleMoreButtonBottomSheet(false)
                storageViewModel.setVisibleDialog(true)
            },
            onClickMoveFolderButton = {
                storageViewModel.setVisibleMoreButtonBottomSheet(false)
                navigateToFolderManage(FolderManageType.CHANGE)

            },
            onDismissRequest = { storageViewModel.setVisibleMoreButtonBottomSheet(false) },
        )

        DoraDialog(
            isShowDialog = storageState.isShowDialog,
            title = stringResource(R.string.dialog_folder_remove_title),
            content = stringResource(R.string.dialog_folder_remove_content),
            confirmBtnText = stringResource(R.string.dialog_folder_remove_button_remove),
            disMissBtnText = stringResource(R.string.dialog_folder_remove_button_cancel),
            onDisMissRequest = { storageViewModel.setVisibleDialog(false) },
            onClickConfirmBtn = { storageViewModel.setVisibleDialog(false) },
        )
    }
}

@Composable
fun StorageScreen(
    navigateToStorageDetail: (StorageFolderItem) -> Unit = {},
    onClickSettingButton: (StorageFolderItem) -> Unit = {},
    onClickAddFolderIcon: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DoraColorTokens.G1),
    ) {
        StorageTopAppBar(
            onClickAddFolderIcon = onClickAddFolderIcon,
        )
        StorageFolderList(
            navigateToStorageDetail = navigateToStorageDetail,
            onClickSettingButton = onClickSettingButton,
        )
    }
}

@Composable
fun StorageTopAppBar(
    modifier: Modifier = Modifier,
    onClickAddFolderIcon: () -> Unit = {},
) {
    Row(
        modifier =
        modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .background(Color.Transparent),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(id = R.string.storage),
            style = DoraTypoTokens.base1Bold,
        )
        IconButton(
            onClick = { onClickAddFolderIcon() },
        ) {
            Icon(
                painter = painterResource(id = com.google.android.material.R.drawable.ic_call_answer),
                contentDescription = "folderIcon",
            )
        }
    }
}

@Preview
@Composable
fun PreviewStorageScreen() {
    StorageRoute()
}
