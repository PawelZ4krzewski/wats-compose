package com.example.wats_compose

import android.app.Application
import com.example.wats_compose.data.module.appModule
import com.example.wats_compose.data.module.firebaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WatsKoinApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WatsKoinApp)
            modules(appModule, firebaseModule)
        }
    }
}