package com.whatever.caramel.external.firebaseMessaging

import android.content.Context
import android.content.Intent

interface NotificationIntentProvider {
    fun provideNotificationIntent(context: Context): Intent
}
