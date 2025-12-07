package com.whatever.caramel.external.crashlytics

interface CaramelCrashlytics {
    fun log(message: String)

    fun recordException(throwable: Throwable)

    fun setKey(
        key: String,
        value: Any?,
    )
}
