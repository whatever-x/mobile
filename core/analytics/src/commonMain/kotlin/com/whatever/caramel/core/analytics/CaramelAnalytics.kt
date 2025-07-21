package com.whatever.caramel.core.analytics

expect fun getCaramelAnalytics(): CaramelAnalytics

interface CaramelAnalytics {
    fun logEvent(
        eventName: String,
        params: Map<String, Any>?,
    )
}
