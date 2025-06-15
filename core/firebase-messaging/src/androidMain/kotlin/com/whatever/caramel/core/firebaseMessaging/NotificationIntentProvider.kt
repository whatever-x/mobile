package com.whatever.caramel.core.firebaseMessaging

import android.content.Context
import android.content.Intent

interface NotificationIntentProvider {

    fun provideNotificationIntent(context: Context): Intent

}