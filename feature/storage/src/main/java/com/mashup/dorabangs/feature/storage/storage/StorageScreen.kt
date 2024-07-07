package com.mashup.dorabangs.feature.storage.storage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
import com.mashup.dorabangs.feature.storage.R
import com.mashup.dorabangs.feature.storage.storage.model.StorageFolderItem

@Composable
fun StorageRoute(
    storageViewModel: StorageViewModel = hiltViewModel(),
    navigateToStorageDetail: (StorageFolderItem) -> Unit = {},
    navigateToCreateFolder: () -> Unit = {},
) {
    StorageScreen(
        navigateToStorageDetail = navigateToStorageDetail,
        onClickAddMoreButton = storageViewModel::showEditFolderBottomSheet,
        onClickAddFolderIcon = navigateToCreateFolder,
    )
}

@Composable
fun StorageScreen(
    navigateToStorageDetail: (StorageFolderItem) -> Unit = {},
    onClickAddMoreButton: (StorageFolderItem) -> Unit = {},
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
            onClickAddMoreButton = onClickAddMoreButton,
        )
    }
}

@Composable
fun StorageTopAppBar(
    onClickAddFolderIcon: () -> Unit = {},
    modifier: Modifier = Modifier,
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
