package com.mashup.dorabangs

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import com.mashup.dorabangs.core.designsystem.theme.DorabangsTheme
import com.mashup.dorabangs.navigation.DoraApp
import com.mashup.dorabangs.splash.FirstEntryScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val overlayPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "허용해야 밖에서 편리하게 세팅한다?", Toast.LENGTH_SHORT).show()
            }
        }

    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermission()

        val firstEntryScreen = intent.getStringExtra("firstEntry")
        val url = intent.data?.path?.substring(1).orEmpty()

        setContent {
            firstEntryScreen?.let {
                if (firstEntryScreen != FirstEntryScreen.Splash.name) {
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager

                    DorabangsTheme {
                        DoraApp(
                            isFirstEntry = firstEntryScreen == FirstEntryScreen.Onboarding.name,
                            hideKeyboardAction = {
                                currentFocus?.let {
                                    imm?.hideSoftInputFromWindow(it.windowToken, 0)
                                }
                            },
                        )
                    }
                }
            }
        }
    }

    private fun checkPermission() {
        if (!Settings.canDrawOverlays(this)) {
            overlayPermissionLauncher.launch(
                Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName"),
                ),
            )
        }
    }
}
