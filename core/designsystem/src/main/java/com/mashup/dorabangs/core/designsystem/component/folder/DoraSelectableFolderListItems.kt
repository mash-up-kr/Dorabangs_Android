package com.mashup.dorabangs.core.designsystem.component.folder

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.component.folder.icselect.IcSelect
import com.mashup.dorabangs.core.designsystem.component.folder.icselect.ImgSelect
import com.mashup.dorabangs.core.designsystem.component.util.LottieLoader
import com.mashup.dorabangs.core.designsystem.component.util.thenIf
import com.mashup.dorabangs.core.designsystem.theme.BottomSheetColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraRoundTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens

@Composable
fun DoraSelectableFolderListItems(
    items: List<DoraSelectableFolderItem>,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onClickItem: (Int) -> Unit = {},
) {
    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            LottieLoader(
                lottieRes = R.raw.spinner,
                modifier = Modifier
                    .padding(top = 40.dp)
                    .size(54.dp)
                    .align(Alignment.TopCenter),
                reverseOnRepeat = true,
                iterations = 200,
            )
        }
    } else {
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
                    index = index
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
}

@Composable
fun DoraFolderSelectableListItem(
    index: Int,
    data: DoraSelectableFolderItem,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 14.dp)
            .size(24.dp),
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
                painter = if (index == 0) {
                    painterResource(id = R.drawable.ic_add_folder_purple)
                } else {
                    painterResource(id = data.vector)
                },
                contentDescription = "",
            )
            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = data.itemName,
                style = DoraTypoTokens.caption3Normal,
                color = if (index == 0) DoraColorTokens.Primary else DoraColorTokens.Black,
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
                vector = R.drawable.ic_3d_folder_small,
                itemName = "새 폴더 추가",
                isSelected = true,
            ),

            DoraSelectableFolderItem(
                vector = R.drawable.ic_3d_folder_small,
                itemName = "폴더 이름",
                isSelected = false,
            ),
        ),
    )
}
