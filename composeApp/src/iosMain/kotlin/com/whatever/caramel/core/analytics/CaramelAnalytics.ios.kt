package com.whatever.caramel.core.analytics

class CaramelAnalyticsImpl : CaramelAnalytics {
    override fun logEvent(eventName: String, params: Map<String, Any>?) {
        iosAnalyticsCallback?.logEvent(eventName, params.toString())
    }

    private fun Map<String, Any>?.toString(): String {
        if (this == null) return ""

        val sb = StringBuilder()
        this.forEach { (key, value) ->
            sb.append("${key}?:${value},")
        }
        return sb.toString()
    }
}

actual fun getCaramelAnalytics(): CaramelAnalytics = CaramelAnalyticsImpl()

interface IosAnalyticsCallback {
    fun logEvent(eventId : String, params : String)
}

private var iosAnalyticsCallback : IosAnalyticsCallback? = null

@Suppress("unused")
fun firebaseCallback(callback : IosAnalyticsCallback) {
    iosAnalyticsCallback = callback
}