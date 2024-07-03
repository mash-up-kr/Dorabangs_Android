package com.mashup.dorabangs

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
