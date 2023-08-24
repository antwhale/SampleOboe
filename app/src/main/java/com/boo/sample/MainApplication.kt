package com.boo.sample

import android.app.Application
import com.boo.sample.base.config.Config
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication: Application() {
    val configPath = "config.json"

    override fun onCreate() {
        super.onCreate()

        Config.init(this, configPath)
    }
}