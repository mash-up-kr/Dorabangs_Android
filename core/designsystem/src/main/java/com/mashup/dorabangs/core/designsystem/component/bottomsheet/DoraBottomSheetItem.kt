package com.mashup.dorabangs.core.designsystem.component.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.mashup.dorabangs.core.designsystem.component.divider.DoraDivider
import com.mashup.dorabangs.core.designsystem.component.util.thenIf
import com.mashup.dorabangs.core.designsystem.theme.BottomSheetColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraRoundTokens

@Composable
fun DoraFolderListItems(
    items: List<BottomSheetItemUIModel>,
    modifier: Modifier = Modifier,
    onClickItem: (Int) -> Unit = {},
) {
    Column(modifier) {
        items.forEachIndexed { index, data ->
            DoraFolderListItem(
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
                    }
                    .height(52.dp)
                    .padding(horizontal = 20.dp),
                data = data,
            )
            if (index != items.lastIndex) {
                DoraDivider(Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
fun DoraFolderListItem(
    data: BottomSheetItemUIModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (data.icon != null) {
            Image(
                painterResource(id = data.icon),
                modifier = Modifier.size(24.dp),
                contentDescription = "",
            )
        }
        Text(
            modifier = Modifier.thenIf(data.icon != null) {
                padding(start = 12.dp)
            },
            text = data.itemName,
            style = data.textStyle,
            color = data.color,
        )
    }
}

@Composable
fun DoraBottomSheetFolderItem(
    data: SelectableBottomSheetItemUIModel?,
    isLastItem: Boolean,
    modifier: Modifier = Modifier,
    onClickItem: () -> Unit = {},
) {
    Column {
        data?.let { item ->
            Row(
                modifier = modifier
                    .background(DoraColorTokens.White)
                    .thenIf(item.isSelected) {
                        background(DoraColorTokens.G1)
                    }
                    .clickable { onClickItem() }
                    .height(52.dp)
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (data.isSelected) {
                    DoraFolderListItem(
                        data = BottomSheetItemUIModel(
                            icon = item.icon,
                            itemName = data.itemName,
                            color = data.color,
                            textStyle = data.selectedTextStyle,
                        ),
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_check),
                        contentDescription = "selectedIcon",
                    )
                } else {
                    DoraFolderListItem(
                        data = BottomSheetItemUIModel(
                            icon = item.icon,
                            itemName = data.itemName,
                            color = data.color,
                            textStyle = item.textStyle,
                        ),
                    )
                }
            }
        }
        if (!isLastItem) {
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

@Preview
@Composable
fun PreviewDoraFolderListItems() {
    DoraFolderListItems(
        modifier = Modifier.fillMaxWidth(),
        items = listOf(
            BottomSheetItemUIModel(R.drawable.ic_plus, "폴더 삭제", color = DoraColorTokens.Alert),
            BottomSheetItemUIModel(R.drawable.ic_plus, "폴더 이름 변경"),
        ),
    )
}

@Preview
@Composable
fun PreviewDoraFolderListItem() {
    DoraBottomSheetFolderItem(
        modifier = Modifier.fillMaxWidth(),
        data = SelectableBottomSheetItemUIModel(
            itemName = "폴더이름",
            isSelected = true,
        ),
        isLastItem = false,
    )
}
