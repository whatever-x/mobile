package com.whatever.caramel.core.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class CaramelAnalyticsImpl(private val context: Context) : CaramelAnalytics {
    private val firebaseAnalytics: FirebaseAnalytics by lazy { FirebaseAnalytics.getInstance(context) }

    override fun logEvent(
        eventName: String,
        params: Map<String, Any>?,
    ) {
        val sendEventBundle =
            params?.run {
                val bundle = Bundle()
                this.forEach { (key, value) ->
                    when (value) {
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

    override fun setUserId(userId: String?) {
        firebaseAnalytics.setUserId(userId)
    }

    override fun resetAnalyticsData() {
        firebaseAnalytics.resetAnalyticsData()
    }

}
