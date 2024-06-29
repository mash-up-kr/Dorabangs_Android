package com.mashup.dorabangs.core.designsystem.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraRoundTokens

@Composable
fun CardProgressBar(
    completedColor: Color,
    remainColor: Color,
    modifier: Modifier = Modifier,
    current: Int = 0,
    total: Int = 100,
) {
    Row(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(current / total.toFloat())
                .fillMaxHeight()
                .background(
                    color = completedColor,
                    shape = DoraRoundTokens.Round99
                )
        )
        Spacer(modifier = Modifier.width(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    color = remainColor,
                    shape = DoraRoundTokens.Round99
                )
        )
    }
}

@Preview
@Composable
fun PreviewCardProgressBar() {
    CardProgressBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(4.dp),
        completedColor = DoraColorTokens.Black,
        remainColor = DoraColorTokens.G4,
        current = 10
    )
}