package com.whatever.caramel.core.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.mp.KoinPlatform

class CaramelAnalyticsImpl : CaramelAnalytics {
    private val context : Context = KoinPlatform.getKoin().get()
    private val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    override fun logEvent(eventName: String, params: Map<String, Any>?) {
        val sendEventBundle = params?.run {
            val bundle = Bundle()
            this.forEach { (key, value) ->
                when(value) {
                    is String -> bundle.putString(key, value)
                    is Int -> bundle.putInt(key, value)
                    is Long -> bundle.putLong(key, value)
                    is Float -> bundle.putFloat(key, value)
                    is Double -> bundle.putDouble(key, value)
                    else -> bundle.putString(key, value.toString())
                }
            }
            bundle
        }
        firebaseAnalytics.logEvent(eventName, sendEventBundle)
    }
}

actual fun getCaramelAnalytics() : CaramelAnalytics = CaramelAnalyticsImpl()