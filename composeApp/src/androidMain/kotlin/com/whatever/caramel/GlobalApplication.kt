package com.whatever.caramel

import android.app.Application
import com.whatever.caramel.di.initKoin
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@GlobalApplication)
        }

        if (BuildConfig.DEBUG) {
            Napier.base(DebugAntilog())
        }
    }
}