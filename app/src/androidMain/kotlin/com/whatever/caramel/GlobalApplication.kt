package com.whatever.caramel

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.appsflyer.AppsFlyerLib
import com.google.firebase.FirebaseApp
import com.whatever.caramel.app.BuildConfig
import com.whatever.caramel.di.initKoin
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import com.whatever.caramel.core.firebaseMessaging.R

class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@GlobalApplication)
        }

        createNotificationChannel()
        FirebaseApp.initializeApp(this)
        AppsFlyerLib.getInstance().init(BuildConfig.APPS_FLYER_KEY, null, this)
        AppsFlyerLib.getInstance().start(this)

        if (BuildConfig.DEBUG) {
            Napier.base(DebugAntilog())
            AppsFlyerLib.getInstance().setDebugLog(true)
        }
    }

    private fun createNotificationChannel() {
        val notificationManager =
            applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channelId = getString(R.string.fcm_id_01)
        val channelName = getString(R.string.fcm_name_01)
        val notificationChannel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )

        notificationManager.createNotificationChannel(notificationChannel)
    }
}