package com.mashup.dorabangs.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mashup.dorabangs.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity: ComponentActivity() {
    private val splashViewModel: SplashViewModel by viewModels()

    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val userId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        splashViewModel.checkUserToken(userId)

        val url = intent.data?.path?.substring(1).orEmpty()

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                (splashViewModel.isSplashShow.value || splashViewModel.firstEntryScreen.value == FirstEntryScreen.Splash) &&
                        url.isBlank()
            }
        }

        setContent {
            val splashShowState by splashViewModel.isSplashShow.collectAsState()

//            if(!splashShowState) {
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//            }

            SplashScreen()
        }
    }
}