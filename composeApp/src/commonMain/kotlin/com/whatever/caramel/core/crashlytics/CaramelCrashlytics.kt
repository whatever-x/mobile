package com.whatever.caramel.core.crashlytics

expect fun getCaramelCrashlytics(): CaramelCrashlytics
interface CaramelCrashlytics {
    fun sendCrashInfo(userId: String? = null, log: String, customs: Map<String, Any>?)
}