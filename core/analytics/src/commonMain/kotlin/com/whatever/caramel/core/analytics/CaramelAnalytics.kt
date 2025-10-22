package com.whatever.caramel.core.analytics

interface CaramelAnalytics {
    fun logEvent(
        eventName: String,
        params: Map<String, Any>?,
    )
}
