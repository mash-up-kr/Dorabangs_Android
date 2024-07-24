package com.mashup.dorabangs.feature.tutorial

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.tooling.preview.Preview
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
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {},
    )

    HomeTutorialScreen(
        onClickBackIcon = onClickBackIcon,
        onClickSettingButton = { sendSystemModal()?.let { launcher.launch(it) } },
    )
}

@Composable
fun HomeTutorialScreen(
    onClickBackIcon: () -> Unit,
    onClickSettingButton: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
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
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = stringResource(id = R.string.home_tutorial_title),
                color = DoraColorTokens.G9,
                style = DoraTypoTokens.Subtitle1Bold,
            )
            Spacer(modifier = Modifier.height(18.5.dp))
            HomeTutorialCont(modifier = Modifier.padding(horizontal = 20.dp))
            Spacer(modifier = Modifier.height(20.dp))
            TutorialVideo(modifier = Modifier)
            Spacer(modifier = Modifier.weight(1f))
            DoraButtons.DoraBtnMaxFull(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                enabled = true,
                buttonText = stringResource(id = R.string.home_tutorial_button),
                onClickButton = onClickSettingButton,
            )
        }
    }
}

@Composable
fun HomeTutorialCont(
    modifier: Modifier = Modifier,
) {
    val tutorialCont = listOf(
        R.string.home_tutorial_cont_1,
        R.string.home_tutorial_cont_2,
        R.string.home_tutorial_cont_3,
    )
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        tutorialCont.forEachIndexed { index, cont ->
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(DoraColorTokens.G2),
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        text = "${index + 1}",
                        color = DoraColorTokens.G7,
                        style = DoraTypoTokens.SMedium,
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = stringResource(id = cont),
                    textAlign = TextAlign.Center,
                    color = DoraColorTokens.G7,
                    style = DoraTypoTokens.SMedium,
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

private fun sendSystemModal(): Intent? {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "Linkit")
        type = "text/plain"
    }
    return Intent.createChooser(sendIntent, "Linkit")
}

@Preview
@Composable
fun PreviewHomeTutorialCont() {
    HomeTutorialCont()
}
