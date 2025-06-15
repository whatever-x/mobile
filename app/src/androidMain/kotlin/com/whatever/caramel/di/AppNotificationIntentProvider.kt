package com.whatever.caramel.di

import android.content.Context
import android.content.Intent
import com.whatever.caramel.MainActivity
import com.whatever.caramel.core.firebaseMessaging.NotificationIntentProvider

class AppNotificationIntentProvider : NotificationIntentProvider {

    override fun provideNotificationIntent(context: Context): Intent =
        Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

}