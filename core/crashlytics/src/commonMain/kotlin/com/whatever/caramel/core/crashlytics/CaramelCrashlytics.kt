package com.whatever.caramel.core.crashlytics

expect fun getCaramelCrashlytics(): CaramelCrashlytics

interface CaramelCrashlytics {
    fun log(message: String)

    fun recordException(throwable: Throwable)

    fun setKey(
        key: String,
        value: Any?,
    )
}
