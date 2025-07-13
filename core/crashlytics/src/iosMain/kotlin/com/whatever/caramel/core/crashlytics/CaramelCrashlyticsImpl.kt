package com.whatever.caramel.core.crashlytics

import firebaseCrashlyticsBridge.FBCrashlyticsBridge
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
class CaramelCrashlyticsImpl(
    private val firebaseCrashlytics: FBCrashlyticsBridge,
) : CaramelCrashlytics {
    override fun log(message: String) {
        firebaseCrashlytics.logWithMessage(message = message)
    }

    override fun recordException(throwable: Throwable) {
        val errorInfo =
            buildString {
                appendLine(throwable.toString())
                throwable.stackTraceToString().lines().forEach { appendLine(it) }
            }
        val errorClassName = throwable::class.simpleName ?: "UnknownException"
        firebaseCrashlytics.recordExceptionWithErrorClassName(errorClassName = errorClassName, errorTrace = errorInfo)
    }

    override fun setKey(
        key: String,
        value: Any?,
    ) {
        firebaseCrashlytics.setKeyWithKey(key = key, value = value)
    }
}
