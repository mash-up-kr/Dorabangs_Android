package com.mashup.dorabangs.feature.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.component.buttons.DoraButtons
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraRoundTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OnBoardingScreen(
    state: OnBoardingState,
    modifier: Modifier = Modifier,
    onClickKeyword: (Int) -> Unit = {},
) {
    Column(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier.padding(top = 104.dp),
            text = "어떤 유형의 URL 카테고리를\n저장하시나요?",
            style = DoraTypoTokens.TitleBold,
            color = DoraColorTokens.G9
        )
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = "1개 이상을 선택해주세요.\n선택하신 키워드로 자동으로 폴더가 생성돼요.",
            style = DoraTypoTokens.caption2Normal,
            color = DoraColorTokens.G4
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
                    onClickKeyword = { onClickKeyword(index) }
                )
            }
        }

        DoraButtons.DoraBtnMaxFull(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            buttonText = "확인",
            enabled = false,
            onClickButton = {}
        )
    }
}

@Composable
private fun KeywordChip(
    keyword: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClickKeyword: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .background(
                color = DoraColorTokens.G1,
                shape = DoraRoundTokens.Round99,
            )
            .clickable(onClick = onClickKeyword)
            .padding(horizontal = 20.dp, vertical = 6.dp),
    ) {
        Text(
            text = keyword,
            style = DoraTypoTokens.caption2Medium
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
                "실화냐!!!!!!"
            )
        )
    )
}