package com.mashup.dorabangs.feature.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.R
import com.mashup.dorabangs.core.designsystem.component.buttons.DoraButtons
import com.mashup.dorabangs.core.designsystem.component.util.thenIf
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraRoundTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OnBoardingScreen(
    state: OnBoardingState,
    modifier: Modifier = Modifier,
    onClickKeyword: (Int) -> Unit = {},
    onClickOkButton: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .fillMaxSize(),
    ) {
        Text(
            modifier = Modifier.padding(top = 104.dp),
            text = stringResource(id = R.string.onboarding_screen_title),
            style = DoraTypoTokens.TitleBold,
            color = DoraColorTokens.G9,
        )
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = stringResource(id = R.string.onboarding_screen_sub_title),
            style = DoraTypoTokens.caption2Normal,
            color = DoraColorTokens.G4,
        )

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            maxItemsInEachRow = 3,
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center,
        ) {
            state.keywords.forEachIndexed { index, keyword ->
                KeywordChip(
                    modifier = Modifier
                        .padding(top = 9.dp)
                        .padding(horizontal = 8.dp),
                    keyword = keyword,
                    isSelected = index in state.selectedIndex,
                    onClickKeyword = { onClickKeyword(index) },
                )
            }
        }

        DoraButtons.DoraBtnMaxFull(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            buttonText = stringResource(id = R.string.onboarding_screen_ok_button),
            enabled = state.selectedIndex.isNotEmpty(),
            onClickButton = onClickOkButton,
        )
    }
}

@Composable
private fun KeywordChip(
    keyword: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClickKeyword: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .background(
                color = DoraColorTokens.G1,
                shape = DoraRoundTokens.Round99,
            )
            .thenIf(isSelected) {
                border(
                    width = 1.5.dp,
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            Color(0xFFA698FF),
                            Color(0xFFE4EDFF),
                            Color(0xFF77C6FF),
                            Color(0xFFABBDFF),
                            Color(0xFFD796FF),
                        ),
                    ),
                    shape = DoraRoundTokens.Round99,
                )
            }
            .clickable(
                onClick = onClickKeyword,
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            )
            .padding(horizontal = 20.dp, vertical = 6.dp),
    ) {
        Text(
            text = keyword,
            style = DoraTypoTokens.caption2Medium,
            color = DoraColorTokens.Black,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewOnBoardingScreen() {
    OnBoardingScreen(
        OnBoardingState(
            keywords = listOf(
                "테스트",
                "입니다",
                "ㅎㅎㅎㅎㅎㅎㅎ",
                "올라",
                "떡볶이",
                "후참잘",
                "별로더라고???",
                "ㅋㅋㅋㅋㅋㅋ",
                "아",
                "퇴근했는데",
                "퇴근못함",
                "길게 한번 가자고고고고곡고고",
                "빨래",
                "널어놨는데",
                "장마철이라",
                "냄새가",
                "꿉꿉해서",
                "다시",
                "빨아야함",
                "이거",
                "실화냐!!!!!!",
            ),
            selectedIndex = setOf(4, 9),
        ),
    )
}
