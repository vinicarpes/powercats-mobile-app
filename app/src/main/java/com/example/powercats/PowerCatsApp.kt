package com.example.powercats

import android.app.Application
import com.example.powercats.di.appModule
import com.example.powercats.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PowerCatsApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PowerCatsApp)
            modules(listOf(appModule, networkModule))
        }
    }
}
