package com.whatever.caramel

import android.app.Application
import com.appsflyer.AppsFlyerLib
import com.google.firebase.FirebaseApp
import com.whatever.caramel.app.BuildConfig
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

        FirebaseApp.initializeApp(this)

        AppsFlyerLib.getInstance().init(BuildConfig.APPS_FLYER_KEY, null, this)
        AppsFlyerLib.getInstance().start(this)

        if (BuildConfig.DEBUG) {
            Napier.base(DebugAntilog())
            AppsFlyerLib.getInstance().setDebugLog(true)
        }
    }
}