package com.mashup.dorabangs.core.designsystem.component.chips

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.component.divider.DoraDivider
import com.mashup.dorabangs.core.designsystem.theme.ChipColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraRoundTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens

@Composable
fun DoraChips(
    chipList: List<FeedUiModel.DoraChipUiModel>,
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    isShowPostCount: Boolean = false,
    onClickChip: (Int) -> Unit = {},
) {
    Column(modifier = modifier) {
        LazyRow(
            modifier = Modifier
                .padding(top = 4.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                Spacer(modifier = Modifier.width(20.dp))
            }
            items(
                count = chipList.size,
                contentType = { "DoraChip" },
            ) { index ->
                DoraChip(
                    doraChipUiModel = chipList[index],
                    isSelected = index == selectedIndex,
                    isShowPostCount = isShowPostCount,
                    onClickChip = { onClickChip(index) },
                )
            }
            item {
                Spacer(modifier = Modifier.width(20.dp))
            }
        }
        DoraDivider()
    }
}

@Composable
fun DoraChip(
    doraChipUiModel: FeedUiModel.DoraChipUiModel,
    modifier: Modifier = Modifier,
    isShowPostCount: Boolean = false,
    isSelected: Boolean = false,
    onClickChip: () -> Unit = {},
) {
    val colorToken = ChipColorTokens(isSelected)
    Row(
        modifier = modifier
            .clip(DoraRoundTokens.Round99)
            .border(
                width = 1.dp,
                color = colorToken.BorderColor,
                shape = DoraRoundTokens.Round99,
            )
            .background(color = colorToken.ContainerColor)
            .clickable(onClick = onClickChip)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (doraChipUiModel.icon != null) {
            Image(
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 4.dp),
                painter = painterResource(id = doraChipUiModel.icon),
                contentDescription = "",
            )
        }
        Text(
            text = doraChipUiModel.title,
            textAlign = TextAlign.Center,
            style = DoraTypoTokens.caption1Medium.copy(
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center,
                    trim = LineHeightStyle.Trim.None,
                ),
            ),
            color = colorToken.OnContainerColor,
        )
        if (isShowPostCount) {
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = doraChipUiModel.postCount.toString(),
                style = DoraTypoTokens.caption1Normal,
                color = colorToken.OnContainerColor2,
            )
        }
    }
}

@Preview
@Composable
fun SelectedDoraChipPreview() {
    DoraChip(
        doraChipUiModel = FeedUiModel.DoraChipUiModel(
            title = "하이?",
        ),
        isSelected = true,
        isShowPostCount = true,
    )
}

@Preview
@Composable
fun UnSelectedDoraChipPreview() {
    DoraChip(
        doraChipUiModel = FeedUiModel.DoraChipUiModel(
            title = "바이?",
        ),
    )
}

@Preview
@Composable
fun SelectedIconDoraChipPreview() {
    DoraChip(
        doraChipUiModel = FeedUiModel.DoraChipUiModel(
            title = "하이?",
            icon = R.drawable.ic_android_white_24dp,
        ),
    )
}

@Preview
@Composable
fun UnSelectedIconDoraChipPreview() {
    DoraChip(
        doraChipUiModel = FeedUiModel.DoraChipUiModel(
            title = "바이?",
            icon = R.drawable.ic_plus,
        ),
    )
}

@Preview
@Composable
fun DoraChipsPreview() {
    DoraChips(
        chipList = listOf(
            FeedUiModel.DoraChipUiModel(
                title = "전체 99+",
                icon = R.drawable.ic_plus,
            ),
            FeedUiModel.DoraChipUiModel(
                title = "하이?",
            ),
            FeedUiModel.DoraChipUiModel(
                title = "바이?",
            ),
            FeedUiModel.DoraChipUiModel(
                title = "바이?",
            ),
            FeedUiModel.DoraChipUiModel(
                title = "바이?",
                icon = R.drawable.ic_plus,
            ),
        ),
        selectedIndex = 0,
    )
}
