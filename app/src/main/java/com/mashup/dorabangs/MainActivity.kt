package com.mashup.dorabangs

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mashup.dorabangs.core.designsystem.theme.DorabangsTheme
import com.mashup.dorabangs.navigation.DoraApp
import com.mashup.dorabangs.splash.FirstEntryScreen
import com.mashup.dorabangs.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val splashViewModel: SplashViewModel by viewModels()
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

        val userId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        splashViewModel.checkUserToken(userId)

        installSplashScreen().apply {
            setKeepOnScreenCondition { splashViewModel.isSplashShow.value || splashViewModel.firstEntryScreen.value == FirstEntryScreen.Splash }
        }

        setContent {
            val firstEntryScreen = splashViewModel.firstEntryScreen.collectAsState()
            if (firstEntryScreen.value != FirstEntryScreen.Splash) {
                DorabangsTheme {
                    DoraApp(isFirstEntry = firstEntryScreen.value == FirstEntryScreen.Onboarding)
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
