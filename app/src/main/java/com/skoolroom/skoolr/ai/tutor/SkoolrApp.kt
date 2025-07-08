package com.skoolroom.skoolr.ai.tutor

import android.app.Application
import com.skoolroom.skoolr.ai.tutor.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SkoolrApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SkoolrApp)
            modules(appModule)
        }
    }
}