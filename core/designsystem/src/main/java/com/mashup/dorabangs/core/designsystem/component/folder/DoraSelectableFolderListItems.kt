package com.mashup.dorabangs.core.designsystem.component.folder

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.component.folder.icfolder.Folder
import com.mashup.dorabangs.core.designsystem.component.folder.icfolder.ImgFolder
import com.mashup.dorabangs.core.designsystem.component.folder.icselect.IcSelect
import com.mashup.dorabangs.core.designsystem.component.folder.icselect.ImgSelect
import com.mashup.dorabangs.core.designsystem.component.util.thenIf
import com.mashup.dorabangs.core.designsystem.theme.BottomSheetColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraRoundTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens

@Composable
fun DoraSelectableFolderListItems(
    items: List<DoraSelectableFolderItem>,
    modifier: Modifier = Modifier,
    onClickItem: (Int) -> Unit = {},
) {
    Column(modifier) {
        items.forEachIndexed { index, data ->
            DoraFolderSelectableListItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClickItem(index) }
                    .thenIf(index == 0) {
                        background(
                            color = BottomSheetColorTokens.ItemColor,
                            shape = DoraRoundTokens.TopRound12,
                        )
                    }
                    .thenIf(index == items.lastIndex) {
                        background(
                            color = BottomSheetColorTokens.ItemColor,
                            shape = DoraRoundTokens.BottomRound12,
                        )
                    },
                data = data,
            )
            if (index != items.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    thickness = 0.5.dp,
                    color = BottomSheetColorTokens.DividerColor,
                )
            }
        }
    }
}

@Composable
fun DoraFolderSelectableListItem(
    data: DoraSelectableFolderItem,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Image(
                modifier = Modifier.size(size = 20.dp),
                imageVector = data.vector,
                contentDescription = "",
            )
            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = data.itemName,
                style = DoraTypoTokens.caption3Normal,
                color = DoraColorTokens.Black,
            )
        }
        if (data.isSelected) {
            Image(
                modifier = Modifier.size(24.dp),
                imageVector = ImgSelect.IcSelect,
                contentDescription = "",
            )
        }
    }
}

@Composable
@Preview
fun DoraSelectableFolderListItemsPreview() {
    DoraSelectableFolderListItems(
        items = listOf(
            DoraSelectableFolderItem(
                vector = Folder.ImgFolder,
                itemName = "새 폴더 추가",
                isSelected = true,
            ),

            DoraSelectableFolderItem(
                vector = Folder.ImgFolder,
                itemName = "폴더 이름",
                isSelected = false,
            ),
        ),
    )
}