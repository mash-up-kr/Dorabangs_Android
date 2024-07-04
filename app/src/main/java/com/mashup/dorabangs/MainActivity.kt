package com.mashup.dorabangs

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mashup.dorabangs.core.designsystem.theme.DorabangsTheme
import com.mashup.dorabangs.navigation.DoraApp
import com.mashup.dorabangs.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        splashViewModel.checkUserToken(userId)

        installSplashScreen().apply {
            setKeepOnScreenCondition { splashViewModel.isSplashShow.value }
        }

        setContent {
            DorabangsTheme {
                DoraApp()
            }
        }
    }
}
