package com.mashup.dorabangs.feature.tutorial

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.component.buttons.DoraButtons
import com.mashup.dorabangs.core.designsystem.component.topbar.DoraTopBar
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
import com.mashup.dorabangs.home.R

@Composable
fun HomeTutorialRoute(
    onClickBackIcon: () -> Unit,
) {
    HomeTutorialScreen(onClickBackIcon = onClickBackIcon)
}

@Composable
fun HomeTutorialScreen(
    onClickBackIcon: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter),
        ) {
            DoraTopBar.BackNavigationTopBar(
                modifier = Modifier.fillMaxWidth(),
                title = "",
                isTitleCenter = true,
                onClickBackIcon = onClickBackIcon,
            )
            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = stringResource(id = R.string.home_tutorial_title),
                color = DoraColorTokens.G9,
                style = DoraTypoTokens.Subtitle1Bold
            )
            Spacer(modifier = Modifier.height(18.5.dp))
            HomeTutorialCont(modifier = Modifier.padding(horizontal = 20.dp))
            TutorialVideo()
            Spacer(modifier = Modifier.weight(1f))
            DoraButtons.DoraBtnMaxFull(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                enabled = true,
                buttonText = stringResource(id = R.string.home_tutorial_button),
                onClickButton = {},
            )
        }
    }
}

@Composable
fun HomeTutorialCont(
    modifier: Modifier = Modifier
) {
    val tutorialCont = listOf(
        R.string.home_tutorial_cont_1,
        R.string.home_tutorial_cont_2,
        R.string.home_tutorial_cont_3
    )
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        tutorialCont.forEachIndexed { index, cont ->
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .size(20.dp)
                        .background(DoraColorTokens.G2),
                    textAlign = TextAlign.Center,
                    text = "${index+1}",
                    color = DoraColorTokens.G7,
                    style = DoraTypoTokens.SMedium
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = stringResource(id = cont),
                    textAlign = TextAlign.Center,
                    color = DoraColorTokens.G7,
                    style = DoraTypoTokens.SMedium
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}