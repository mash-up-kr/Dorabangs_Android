package com.mashup.dorabangs.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.mashup.dorabangs.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        splashViewModel.checkUserToken(userId)

        setContent {
            SplashScreen()

            val firstEntryScreen = splashViewModel.firstEntryScreen.collectAsState()
            if (firstEntryScreen.value != FirstEntryScreen.Splash) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("firstEntry", firstEntryScreen.value.name)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                finish()
            }
        }
    }
}
