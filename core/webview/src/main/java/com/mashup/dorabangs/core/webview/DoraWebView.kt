package com.mashup.dorabangs.core.webview

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.WindowManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.mashup.dorabangs.core.designsystem.component.topbar.DoraTopBar
import com.mashup.dorabangs.core.summary.AISummaryTopButton

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun DoraWebView(
    url: String,
    navigateToPopBackStack: () -> Unit,
    navigateToAISummary: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberWebViewState(url)
    val context = LocalContext.current
    val webViewChromeClient = remember { CustomWebChromeClient(context) }

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        DoraTopBar.BackWithActionIconTopBar(
            modifier = Modifier.fillMaxWidth(),
            title = "",
            isTitleCenter = true,
            onClickBackIcon = navigateToPopBackStack,
            isShowBottomDivider = true,
            actionIcon = {
                AISummaryTopButton(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 20.dp),
                    onClickSummary = navigateToAISummary,
                )
            },
        )
        WebView(
            state = state,
            modifier = Modifier.fillMaxSize(),
            onCreated = { webView ->
                webView.settings.javaScriptEnabled = true
            },
            chromeClient = webViewChromeClient,
        )
    }
}

class CustomWebChromeClient(
    private val context: Context,
) : AccompanistWebChromeClient() {
    private val windowManager: WindowManager by lazy {
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    private var customView: View? = null
    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
        if (customView != null) {
            callback?.onCustomViewHidden()
            return
        }

        customView = view
        windowManager.addView(customView, WindowManager.LayoutParams())
    }

    override fun onHideCustomView() {
        super.onHideCustomView()
        windowManager.removeView(customView)
        customView = null
    }
}
