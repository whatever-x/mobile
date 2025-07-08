package com.whatever.caramel.core.crashlytics

interface CaramelCrashlytics {
    fun log(message: String)

    fun recordException(throwable: Throwable)

    fun setKey(
        key: String,
        value: Any?,
    )
}
