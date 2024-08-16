package com.mashup.dorabangs

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity

class DoraShareActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shareText = handleShare()
        if (shareText.isNotBlank() && shareText != "Linkit") {
            Intent(this, DoraOverlayService::class.java)
                .apply {
                    putExtra(URL, shareText)
                }.also {
                    startService(it)
                    finish()
                }
        } else {
            finish()
        }
    }

    private fun handleShare(): String {
        val type = intent.type
        if (Intent.ACTION_SEND == intent.action && type != null) {
            if ("text/plain" == type) {
                return handleSendText()
            }
        }
        return ""
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleSendText()
    }

    private fun handleSendText(): String {
        return intent.getStringExtra(Intent.EXTRA_TEXT).orEmpty()
    }

    companion object {
        private const val URL = "SHARED_URL"
    }
}
