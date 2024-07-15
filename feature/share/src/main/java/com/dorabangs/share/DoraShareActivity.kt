package com.dorabangs.share

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity

class DoraShareActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (handleShare().isNotBlank()) {
            val intent = Intent(this, DoraOverlayService::class.java)
            startService(intent)
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
}
