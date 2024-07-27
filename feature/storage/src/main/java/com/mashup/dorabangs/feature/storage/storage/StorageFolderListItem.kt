package com.mashup.dorabangs.feature.storage.storage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.domain.model.FolderType
import com.mashup.dorabangs.feature.storage.storage.model.StorageListState
import com.mashup.dorabangs.core.designsystem.R as coreR

@Composable
fun StorageFolderList(
    storageState: StorageListState,
    navigateToStorageDetail: (String) -> Unit,
    onClickSettingButton: (Folder) -> Unit = {},
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 20.dp),
    ) {
        item {
            Spacer(modifier = Modifier.height(20.dp))
            StorageDefaultFolder(
                defaultFolderList = storageState.defaultStorageFolderList,
                navigateToStorageDetail = navigateToStorageDetail,
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        itemsIndexed(storageState.customStorageFolderList) { idx, item ->
            StorageListItem(
                item = item,
                isFirstItem = idx == 0,
                isLastItem = idx == storageState.customStorageFolderList.lastIndex,
                navigateToStorageDetail = { navigateToStorageDetail(item.id.orEmpty()) },
                onClickSettingButton = { onClickSettingButton(item) },
            )
            if (idx != storageState.customStorageFolderList.lastIndex) {
                HorizontalDivider(
                    modifier =
                    Modifier
                        .height(0.5.dp)
                        .background(color = DoraColorTokens.G1),
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@Composable
fun StorageDefaultFolder(
    defaultFolderList: List<Folder>,
    navigateToStorageDetail: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        defaultFolderList.forEachIndexed { idx, item ->
            StorageListItem(
                item = item,
                isFirstItem = idx == 0,
                isLastItem = idx == defaultFolderList.lastIndex,
                navigateToStorageDetail = { navigateToStorageDetail(item.id.orEmpty()) },
            )
            if (idx != defaultFolderList.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier
                        .height(0.5.dp)
                        .background(color = DoraColorTokens.G1),
                )
            }
        }
    }
}

@Composable
fun StorageListItem(
    item: Folder,
    isFirstItem: Boolean = false,
    isLastItem: Boolean = false,
    navigateToStorageDetail: () -> Unit = {},
    onClickSettingButton: () -> Unit = {},
) {
    val shape =
        if (isFirstItem) {
            RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
        } else if (isLastItem) {
            RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
        } else {
            RectangleShape
        }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = DoraColorTokens.P1, shape = shape)
            .padding(vertical = 14.dp, horizontal = 12.dp),
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable { navigateToStorageDetail() },
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = getFolderIcon(item.folderType)),
                contentDescription = "folderIcon",
            )
            Text(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center,
                text = item.name,
                color = DoraColorTokens.G9,
                style = DoraTypoTokens.caption3Medium,
            )
        }
        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
        ) {
            Text(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .align(Alignment.CenterVertically),
                text = "${item.postCount}",
                color = DoraColorTokens.G4,
                style = DoraTypoTokens.caption3Medium,
            )
            val isDefault = item.folderType != FolderType.CUSTOM
            val icon = if (isDefault) coreR.drawable.ic_chevron_right_m_gray else coreR.drawable.ic_more_gray
            Image(
                modifier = Modifier.clickable { if (isDefault) navigateToStorageDetail() else onClickSettingButton() },
                painter = painterResource(id = icon),
                contentDescription = "folderIcon",
            )
        }
    }
}

private fun getFolderIcon(type: FolderType): Int {
    return when (type) {
        FolderType.ALL -> coreR.drawable.ic_3d_all_big
        FolderType.FAVORITE -> coreR.drawable.ic_3d_bookmark_big
        FolderType.DEFAULT -> coreR.drawable.ic_3d_pin_big
        FolderType.CUSTOM -> coreR.drawable.ic_3d_folder_big
        else -> coreR.drawable.ic_3d_folder_big
    }
}

@Preview
@Composable
fun PreviewStorageFolderList() {
    StorageFolderList(
        storageState = StorageListState(),
        navigateToStorageDetail = {},
    )
}
