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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraGradientToken
import com.mashup.dorabangs.core.designsystem.theme.DoraRoundTokens
import kotlinx.coroutines.delay

@Composable
fun CardProgressBar(
    completedColor: Color,
    remainColor: Brush,
    modifier: Modifier = Modifier,
    initial: Int = 0,
    total: Int = 1000,
) {
    var percentage by rememberSaveable { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        for (time in initial..(total * 0.8).toInt()) {
            delay(10)
            percentage = time.toFloat() / total
        }
    }

    Row(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(percentage)
                .fillMaxHeight()
                .background(
                    color = completedColor,
                    shape = DoraRoundTokens.Round99,
                ),
        )
        Spacer(modifier = Modifier.width(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    brush = remainColor,
                    shape = DoraRoundTokens.Round99,
                ),
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
        remainColor = DoraGradientToken.Gradient2,
        initial = 800,
    )
}
