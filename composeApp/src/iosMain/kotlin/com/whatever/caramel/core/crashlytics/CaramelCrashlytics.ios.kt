package com.whatever.caramel.core.crashlytics

class CaramelCrashlyticsImpl : CaramelCrashlytics {
    override fun sendCrashInfo(userId: String?, log: String, customs: Map<String, Any>?) {
        iosCrashlyticsCallback?.sendCrashInfo(userId, log, customs.toString())
    }

    private fun Map<String, Any>?.toString(): String {
        if (this == null) return ""

        val sb = StringBuilder()
        this.forEach { (key, value) ->
            sb.append("${key}:${value},")
        }
        return sb.toString()
    }
}

actual fun getCaramelCrashlytics(): CaramelCrashlytics = CaramelCrashlyticsImpl()

interface IosCrashlyticsCallback {
    fun sendCrashInfo(userId : String?, log : String, params : String)
}

private var iosCrashlyticsCallback : IosCrashlyticsCallback? = null

@Suppress("unused")
fun firebaseCallback(callback : IosCrashlyticsCallback) {
    iosCrashlyticsCallback = callback
}