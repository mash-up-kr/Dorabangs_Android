package com.mashup.dorabangs.feature.storage.storage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import com.mashup.dorabangs.feature.storage.storage.model.StorageFolderItem
import com.mashup.dorabangs.feature.storage.storage.model.StorageListState

@Composable
fun StorageFolderList(
    customList: List<StorageFolderItem> = StorageListState.customFolderList(),
    navigateToStorageDetail: (StorageFolderItem) -> Unit = {},
    onClickAddMoreButton: (StorageFolderItem) -> Unit = {},
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 20.dp),
    ) {
        item {
            Spacer(modifier = Modifier.height(20.dp))
            StorageDefaultFolder()
            Spacer(modifier = Modifier.height(20.dp))
        }
        itemsIndexed(customList) { idx, item ->
            val isFirstItem = idx == 0
            val isLastItem = idx == customList.lastIndex
            StorageListItem(
                item = item,
                isFirstItem = isFirstItem,
                isLastItem = isLastItem,
                navigateToStorageDetail = { navigateToStorageDetail(item) },
                onClickAddMoreButton = { onClickAddMoreButton(item) },
            )
            if (!isLastItem) {
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
    list: List<StorageFolderItem> = StorageListState.defaultFolderList(),
    navigateToStorageDetail: (StorageFolderItem) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        list.forEachIndexed { idx, item ->
            val isFirstItem = idx == 0
            val isLastItem = idx == list.lastIndex
            StorageListItem(
                item = item,
                isFirstItem = isFirstItem,
                isLastItem = isLastItem,
                navigateToStorageDetail = { navigateToStorageDetail(item) },
            )
            if (idx != list.lastIndex) {
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
    item: StorageFolderItem,
    isDefault: Boolean = false,
    isFirstItem: Boolean = false,
    isLastItem: Boolean = false,
    navigateToStorageDetail: () -> Unit = {},
    onClickAddMoreButton: () -> Unit = {},
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
            modifier = Modifier.align(Alignment.CenterStart).clickable { navigateToStorageDetail() },
        ) {
            Icon(
                painter = painterResource(id = androidx.core.R.drawable.ic_call_answer),
                contentDescription = "folderIcon",
            )
            Text(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center,
                text = item.folderTitle,
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
            val icon =
                if (isDefault) {
                    androidx.core.R.drawable.ic_call_answer
                } else {
                    androidx.core.R.drawable.ic_call_answer
                }
            Icon(
                modifier = Modifier.clickable { if (isDefault) navigateToStorageDetail() else onClickAddMoreButton() },
                painter = painterResource(id = icon),
                contentDescription = "folderIcon",
            )
        }
    }
}

@Preview
@Composable
fun PreviewStorageFolderList() {
    StorageFolderList()
}
