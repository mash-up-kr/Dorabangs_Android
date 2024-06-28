package com.mashup.dorabangs.core.designsystem.component.chips

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
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
import com.mashup.dorabangs.core.designsystem.theme.ChipsColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraRoundTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens

@Composable
fun DoraChips(
    chipList: List<DoraChipUiModel>,
    modifier: Modifier = Modifier,
    onClickChip: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        chipList.forEach {
            DoraChip(
                doraChipUiModel = it,
                onClickChip = onClickChip
            )
        }
    }
}

@Composable
fun DoraChip(
    doraChipUiModel: DoraChipUiModel,
    modifier: Modifier = Modifier,
    onClickChip: () -> Unit = {}
) {
    val colorToken = ChipsColorTokens(doraChipUiModel.isSelected)
    Row(
        modifier = modifier
            .clip(DoraRoundTokens.Round99)
            .border(
                width = 1.dp,
                color = colorToken.ChipBorderColor,
                shape = DoraRoundTokens.Round99
            )
            .background(color = colorToken.ChipContainerColor)
            .clickable(onClick = onClickChip)
            .padding(horizontal = 12.dp, vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (doraChipUiModel.icon != null) {
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 4.dp),
                painter = painterResource(id = doraChipUiModel.icon),
                contentDescription = ""
            )
        }
        Text(
            text = doraChipUiModel.title,
            textAlign = TextAlign.Center,
            style = DoraTypoTokens.caption1Medium.copy(
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center,
                    trim = LineHeightStyle.Trim.None
                )
            ),
            color = colorToken.ChipOnContainerColor
        )
    }
}

@Preview
@Composable
fun SelectedDoraChipPreview() {
    DoraChip(
        doraChipUiModel = DoraChipUiModel(
            title = "하이?",
            isSelected = true
        ),
    )
}

@Preview
@Composable
fun UnSelectedDoraChipPreview() {
    DoraChip(
        doraChipUiModel = DoraChipUiModel(
            title = "바이?",
            isSelected = false
        )
    )
}

@Preview
@Composable
fun SelectedIconDoraChipPreview() {
    DoraChip(
        doraChipUiModel = DoraChipUiModel(
            title = "하이?",
            icon = R.drawable.ic_android_white_24dp,
            isSelected = true
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
            isSelected = false
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
                isSelected = false
            ),
            DoraChipUiModel(
                title = "하이?",
                isSelected = true
            ),
            DoraChipUiModel(
                title = "바이?",
                isSelected = false
            ),
            DoraChipUiModel(
                title = "바이?",
                isSelected = false
            ),
            DoraChipUiModel(
                title = "바이?",
                icon = R.drawable.ic_plus,
                isSelected = false
            )
        )
    )
}