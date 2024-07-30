package com.mashup.feature.classification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.component.buttons.DoraButtons
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
import com.mashup.dorabangs.core.designsystem.theme.DorabangsTheme

@Composable
fun ClassificationCompleteScreen(
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.ai_classification_complete),
            style = DoraTypoTokens.Subtitle2Medium,
            color = DoraColorTokens.G9,
        )
        Spacer(modifier = Modifier.height(16.dp))
        DoraButtons.DoraSmallConfirmBtn(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 30.dp, vertical = 8.dp)
                .wrapContentWidth(),
            buttonText = stringResource(id = R.string.ai_classification_navigate_home),
            onClickButton = { navigateToHome() },
        )
    }
}

@Composable
@Preview
fun ClassificationCompleteScreenPreview() {
    DorabangsTheme {
        ClassificationCompleteScreen(
            navigateToHome = {},
        )
    }
}
