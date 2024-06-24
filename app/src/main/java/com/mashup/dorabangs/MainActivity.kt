package com.mashup.dorabangs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mashup.dorabangs.core.designsystem.theme.DorabangsTheme
import com.mashup.dorabangs.navigation.DoraApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DorabangsTheme {
                DoraApp()
            }
        }
    }
}
