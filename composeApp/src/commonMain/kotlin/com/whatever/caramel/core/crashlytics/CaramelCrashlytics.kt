package com.whatever.caramel.core.crashlytics

import com.whatever.caramel.core.analytics.CaramelAnalytics

expect fun getCaramelCrashlytics(): CaramelCrashlytics
interface CaramelCrashlytics {
    fun sendCrashInfo(userId: String? = null, log: String, customs: Map<String, Any>?)
}