package com.whatever.caramel.core.crashlytics

class IosCaramelCrashlyticsImpl : CaramelCrashlytics {
    override fun log(message: String) {
        iosCrashlyticsCallback?.log(message)
    }

    override fun recordException(throwable: Throwable) {
        val errorInfo =
            buildString {
                appendLine(throwable.toString())
                throwable.stackTraceToString().lines().forEach { appendLine(it) }
            }
        iosCrashlyticsCallback?.recordException(errorInfo)
    }

    override fun setKey(
        key: String,
        value: Any?,
    ) {
        iosCrashlyticsCallback?.setKey(key, value)
    }
}

actual fun getCaramelCrashlytics(): CaramelCrashlytics = IosCaramelCrashlyticsImpl()

interface IosCrashlyticsCallback {
    fun log(message: String)

    fun recordException(errorTrace: String)

    fun setKey(
        key: String,
        value: Any?,
    )
}

private var iosCrashlyticsCallback: IosCrashlyticsCallback? = null

@Suppress("unused")
fun setFirebaseCrashlyticsCallback(callback: IosCrashlyticsCallback) {
    iosCrashlyticsCallback = callback
}
