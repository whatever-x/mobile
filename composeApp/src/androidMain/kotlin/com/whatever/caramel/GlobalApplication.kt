package com.whatever.caramel

import android.app.Application
import com.whatever.caramel.di.initKoin
import org.koin.android.ext.koin.androidContext

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@GlobalApplication)
        }
    }
}