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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mashup.dorabangs.core.designsystem.component.bottomsheet.DoraBottomSheet
import com.mashup.dorabangs.core.designsystem.component.dialog.DoraDialog
import com.mashup.dorabangs.core.designsystem.component.toast.DoraToast
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.feature.folders.model.FolderManageType
import com.mashup.dorabangs.feature.storage.R
import com.mashup.dorabangs.feature.storage.storage.model.StorageListSideEffect
import com.mashup.dorabangs.feature.storage.storage.model.StorageListState
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import com.mashup.dorabangs.core.designsystem.R as coreR

@Composable
fun StorageRoute(
    isChangedData: Boolean = false,
    isRemoveSuccess: Boolean = false,
    storageViewModel: StorageViewModel = hiltViewModel(),
    navigateToStorageDetail: (Folder) -> Unit,
    navigateToFolderManage: (FolderManageType, String) -> Unit,
) {
    val context = LocalContext.current
    val storageState by storageViewModel.collectAsState()
    val toastSnackBarHostState by remember { mutableStateOf(SnackbarHostState()) }
    val scope = rememberCoroutineScope()

    storageViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is StorageListSideEffect.NavigateToFolderManage -> navigateToFolderManage(FolderManageType.CHANGE, storageState.selectedFolderId)
            is StorageListSideEffect.ShowFolderRemoveToastSnackBar -> {
                scope.launch {
                    toastSnackBarHostState.showSnackbar(message = storageState.toastState.text)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        if (isChangedData) storageViewModel.getFolderList()
        if (isRemoveSuccess) storageViewModel.setToastState(context.getString(R.string.toast_remove_folder))
    }

    Box {
        StorageScreen(
            storageState = storageState,
            navigateToStorageDetail = navigateToStorageDetail,
            onClickSettingButton = { folderItem ->
                storageViewModel.setSelectFolderId(folderId = folderItem.id.orEmpty())
                storageViewModel.setVisibleMoreButtonBottomSheet(visible = true)
            },
            onClickAddFolderIcon = { navigateToFolderManage(FolderManageType.CREATE, storageState.selectedFolderId) },
        )
        DoraBottomSheet.MoreButtonBottomSheet(
            modifier = Modifier.height(320.dp),
            isShowSheet = storageState.isShowMoreButtonSheet,
            firstItemName = R.string.storage_more_bottom_sheet_folder_remove,
            secondItemName = R.string.storage_more_bottom_sheet_folder_name_change,
            onClickDeleteLinkButton = {
                storageViewModel.setVisibleMoreButtonBottomSheet(false)
                storageViewModel.setVisibleDialog(true)
            },
            onClickMoveFolderButton = {
                storageViewModel.setVisibleMoreButtonBottomSheet(visible = false, isNavigate = true)
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
            onClickConfirmBtn = {
                storageViewModel.deleteFolder(folderId = storageState.selectedFolderId)
                storageViewModel.setVisibleDialog(false)
            },
        )
        DoraToast(
            text = storageState.toastState.text,
            toastStyle = storageState.toastState.toastStyle,
            snackBarHostState = toastSnackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 20.dp),
        )
    }
}

@Composable
fun StorageScreen(
    storageState: StorageListState,
    navigateToStorageDetail: (Folder) -> Unit,
    onClickSettingButton: (Folder) -> Unit,
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
            storageState = storageState,
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
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(id = R.string.storage),
            style = DoraTypoTokens.base1Bold,
        )
        IconButton(
            onClick = { onClickAddFolderIcon() },
        ) {
            Icon(
                painter = painterResource(id = coreR.drawable.ic_add_folder),
                contentDescription = "folderIcon",
            )
        }
    }
}

@Preview
@Composable
fun PreviewStorageScreen() {
    StorageRoute(
        navigateToStorageDetail = {},
        navigateToFolderManage = { type, id -> },
    )
}
