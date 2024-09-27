package com.mashup.feature.classification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraTypoTokens
import com.mashup.dorabangs.core.designsystem.theme.DorabangsTheme

@Composable
fun ClassificationAIBanner(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = DoraColorTokens.G8)
            .padding(start = 12.dp)
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = com.mashup.dorabangs.core.designsystem.R.drawable.ic_3d_all_big),
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = stringResource(id = R.string.ai_classification_ai_banner),
            style = DoraTypoTokens.SNormal,
            color = DoraColorTokens.G2,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ClassificationAIBannerPreView() {
    DorabangsTheme {
        ClassificationAIBanner(modifier = Modifier)
    }
}