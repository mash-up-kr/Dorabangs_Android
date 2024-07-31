package com.mashup.dorabangs.core.designsystem.component.chips

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import com.mashup.dorabangs.core.designsystem.theme.ChipColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraRoundTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens

@Composable
fun DoraChips(
    chipList: List<DoraChipUiModel>,
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    onClickChip: (Int) -> Unit = {},
) {
    LazyRow(
        modifier = modifier
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            Spacer(modifier = Modifier.width(12.dp))
        }
        items(chipList.size) { index ->
            DoraChip(
                doraChipUiModel = chipList[index],
                isSelected = index == selectedIndex,
                onClickChip = { onClickChip(index) },
            )
        }
        item {
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}

@Composable
fun DoraChip(
    doraChipUiModel: DoraChipUiModel,
    modifier: Modifier = Modifier,
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
    }
}

@Preview
@Composable
fun SelectedDoraChipPreview() {
    DoraChip(
        doraChipUiModel = DoraChipUiModel(
            title = "하이?",
        ),
    )
}

@Preview
@Composable
fun UnSelectedDoraChipPreview() {
    DoraChip(
        doraChipUiModel = DoraChipUiModel(
            title = "바이?",
        ),
    )
}

@Preview
@Composable
fun SelectedIconDoraChipPreview() {
    DoraChip(
        doraChipUiModel = DoraChipUiModel(
            title = "하이?",
            icon = R.drawable.ic_android_white_24dp,
        ),
    )
}

@Preview
@Composable
fun UnSelectedIconDoraChipPreview() {
    DoraChip(
        doraChipUiModel = DoraChipUiModel(
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
            DoraChipUiModel(
                title = "전체 99+",
                icon = R.drawable.ic_plus,
            ),
            DoraChipUiModel(
                title = "하이?",
            ),
            DoraChipUiModel(
                title = "바이?",
            ),
            DoraChipUiModel(
                title = "바이?",
            ),
            DoraChipUiModel(
                title = "바이?",
                icon = R.drawable.ic_plus,
            ),
        ),
        selectedIndex = 0,
    )
}
