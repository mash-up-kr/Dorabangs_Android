package com.mashup.dorabangs.feature.tutorial

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.mashup.dorabangs.home.R

@Composable
fun TutorialVideo() {
    val context = LocalContext.current
    val exoplayer = ExoPlayer.Builder(context).build()

    val mediaItem = MediaItem.fromUri("android.resource://com.mashup.dorabangs.feature.home/${R.raw.test_youngk}")
    exoplayer.apply {
        setMediaItem(mediaItem)
        repeatMode = ExoPlayer.REPEAT_MODE_ALL
        prepare()
        playWhenReady = true
    }

    AndroidView(
        factory = { factoryContext ->
            PlayerView(factoryContext).apply {
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                )
                this.player = exoplayer
                this.useController = false
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
    )
    LifecycleStartEffect(Lifecycle.Event.ON_START) {
        onStopOrDispose {
            exoplayer.release()
        }
    }
}
