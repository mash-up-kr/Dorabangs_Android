package com.mashup.dorabangs.core.designsystem.component.card

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.mashup.dorabangs.core.designsystem.theme.DoraColorTokens
import com.mashup.dorabangs.core.designsystem.theme.DoraRoundTokens
import java.util.Random

@Composable
fun TextLoadingSkeleton(
    primaryColor: Color,
    containerColor: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.animatedGradient(
            primaryColor = primaryColor,
            containerColor = containerColor,
        ),
    )
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.animatedGradient(
    primaryColor: Color,
    containerColor: Color,
): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = "")
    val colors = listOf(
        primaryColor,
        containerColor,
        containerColor,
        primaryColor,
    )
    val offsetXAnimation by transition.animateFloat(
        initialValue = -size.width.toFloat(),
        targetValue = size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
            initialStartOffset = StartOffset(Random().nextInt(1500)),
        ),
        label = "gradientAnimation",
    )
    background(
        brush = Brush.linearGradient(
            colors = colors,
            start = Offset(x = offsetXAnimation, y = 0f),
            end = Offset(x = offsetXAnimation + size.width.toFloat(), y = size.height.toFloat()),
        ),
        shape = DoraRoundTokens.Round4,
    )
        .onGloballyPositioned {
            size = it.size
        }
}

@Preview
@Composable
fun TextLoadingSkeletonPreview() {
    TextLoadingSkeleton(
        DoraColorTokens.Primary500,
        DoraColorTokens.White,
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp),
    )
}
