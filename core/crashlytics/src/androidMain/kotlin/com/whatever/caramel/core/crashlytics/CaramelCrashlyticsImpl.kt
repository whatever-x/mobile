package com.whatever.caramel.core.crashlytics

import com.google.firebase.crashlytics.FirebaseCrashlytics

class CaramelCrashlyticsImpl : CaramelCrashlytics {
    private val crashlytics =
        FirebaseCrashlytics.getInstance().apply {
            isCrashlyticsCollectionEnabled = !BuildConfig.DEBUG
        }

    override fun log(message: String) {
        crashlytics.log(message)
    }

    override fun recordException(throwable: Throwable) {
        crashlytics.recordException(throwable)
    }

    override fun setKey(
        key: String,
        value: Any?,
    ) {
        when (value) {
            is String -> crashlytics.setCustomKey(key, value)
            is Int -> crashlytics.setCustomKey(key, value)
            is Float -> crashlytics.setCustomKey(key, value)
            is Double -> crashlytics.setCustomKey(key, value)
            is Boolean -> crashlytics.setCustomKey(key, value)
            else -> crashlytics.setCustomKey(key, value.toString())
        }
    }
}
