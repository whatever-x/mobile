package com.whatever.caramel.core.crashlytics

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.setCustomKeys

class CaramelCrashlyticsImpl : CaramelCrashlytics {
    private val firebaseCrashlytics = FirebaseCrashlytics.getInstance()

    override fun sendCrashInfo(userId: String?, log: String, customs: Map<String, Any>?) {
        with(firebaseCrashlytics) {
            if(userId != null){
                setUserId(userId)
            }
            log(log)
            customs?.forEach { (key, value) ->
                setCustomKeys {
                    when(value) {
                        is String -> key(key, value)
                        is Long -> key(key, value)
                        is Int -> key(key, value)
                        is Float -> key(key, value)
                        is Double -> key(key, value)
                        else ->  key(key, value.toString())
                    }
                }
            }
        }
    }
}

actual fun getCaramelCrashlytics() : CaramelCrashlytics = CaramelCrashlyticsImpl()