package com.mashup.dorabangs.core.webview

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.mashup.dorabangs.core.designsystem.component.topbar.DoraTopBar

@Composable
fun DoraWebView(
    url: String,
    navigateToPopBackStack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val webView = remember { WebView(context) }

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        DoraTopBar.BackNavigationTopBar(
            modifier = Modifier.fillMaxWidth(),
            title = "",
            isTitleCenter = true,
            onClickBackIcon = { navigateToPopBackStack() },
        )
        AndroidView(factory = {
            webView.apply {
                this.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )
                this.webViewClient = WebViewClient()
            }
        }, update = {
            it.loadUrl(url)
        })
    }
}
