package com.mashup.dorabangs

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    //    @Inject
    //    lateinit var flipperNetworkPlugin: NetworkFlipperPlugin
    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this, false)

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            val client = AndroidFlipperClient.getInstance(this).apply {
                    addPlugin(
                    InspectorFlipperPlugin(
                        this@MyApplication,
                        DescriptorMapping.withDefaults()
                    )
                )
                    addPlugin(CrashReporterPlugin.getInstance())
//                addPlugin(flipperNetworkPlugin)
                addPlugin(SharedPreferencesFlipperPlugin(this@MyApplication))
            }
            client.start()
        }
    }
}