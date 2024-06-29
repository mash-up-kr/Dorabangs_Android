package com.mashup.dorabangs.core.designsystem.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.component.util.thenIf
import com.mashup.dorabangs.core.designsystem.theme.BottomSheetColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraRoundTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens

@Composable
fun DoraBottomSheetItems(
    items: List<BottomSheetItemUIModel>,
    modifier: Modifier = Modifier,
    onClickItem: (Int) -> Unit = {},
) {
    Column(modifier) {
        items.forEachIndexed { index, data ->
            DoraBottomSheetItem(
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
                    .padding(16.dp),
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
fun DoraBottomSheetItem(
    data: BottomSheetItemUIModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painterResource(id = data.icon),
            contentDescription = "",
        )
        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = data.itemName,
            style = DoraTypoTokens.caption3Normal,
            color = data.color,
        )
    }
}

@Preview
@Composable
fun PreviewDoraBottomSheetItems() {
    DoraBottomSheetItems(
        modifier = Modifier.fillMaxWidth(),
        items = listOf(
            BottomSheetItemUIModel(R.drawable.ic_plus, "폴더 삭제", color = DoraColorTokens.Alert),
            BottomSheetItemUIModel(R.drawable.ic_plus, "폴더 이름 변경"),
        ),
    )
}
