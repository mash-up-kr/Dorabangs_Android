package com.mashup.dorabangs.feature.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier
                .padding(top = 113.dp),
            text = stringResource(id = R.string.onboarding_screen_title),
            style = DoraTypoTokens.Subtitle1Bold,
            color = DoraColorTokens.G9,
        )
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = stringResource(id = R.string.onboarding_screen_sub_title),
            style = DoraTypoTokens.caption3Normal,
            color = DoraColorTokens.G5,
        )

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            maxItemsInEachRow = 4,
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center,
        ) {
            state.keywords.forEachIndexed { index, keyword ->
                KeywordChip(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .padding(horizontal = 6.dp),
                    keyword = keyword,
                    isSelected = index in state.selectedIndex,
                    onClickKeyword = { onClickKeyword(index) },
                )
            }
        }

        DoraButtons.DoraBtnMaxFull(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            buttonText = stringResource(id = R.string.onboarding_screen_complete_button),
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
                this.
                    background(
                        color = DoraColorTokens.G9,
                        shape = DoraRoundTokens.Round99,
                    )
                    .border(
                        brush = Brush.linearGradient(
                            listOf(
                                Color(0xff7764FF),
                                Color(0xffE7E7FF),
                                Color(0xffFFE7F3),
                            )
                        ),
                        width = 1.dp,
                        shape = DoraRoundTokens.Round99,
                    )
            }
            .clip(DoraRoundTokens.Round99)
            .clickable(onClick = onClickKeyword)
            .padding(horizontal = 20.dp, vertical = 6.dp),
    ) {
        if (!isSelected) {
            Text(
                text = keyword,
                style = DoraTypoTokens.caption2Normal,
                color = DoraColorTokens.Black,
            )
        } else {
            Text(
                text = keyword,
                style = DoraTypoTokens.caption2Medium.copy(
                    brush = Brush.linearGradient(
                        listOf(
                            Color(0xffCDC6FF),
                            Color(0xffE7E7FF),
                            Color(0xffFFC8E2),
                        )
                    )
                ),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 390, heightDp = 844)
@Composable
fun PreviewOnBoardingScreen() {
    OnBoardingScreen(
        OnBoardingState(
            keywords = listOf(
                "뉴진스 민지",
                "디자인",
                "소셜 미디어",
                "개발",
                "쇼핑",
                "#Keyword",
                "여행",
                "음악",
                "UX",
                "자기개발",
                "금융",
                "한화 또졌어ㅜㅜ",
                "오락",
                "비즈니스",
                "건강",
                "부동산",
                "세계",
                "예술",
                "스포츠",
                "경영",
                "운동",
                "기술",
                "영화",
                "책",
                "사진",
                "교육",
                "과학",
                "패션",
                "정치",
                "생산성",
                "환경"
            ),
            selectedIndex = setOf(4, 9),
        ),
    )
}
