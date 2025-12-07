package com.whatever.caramel.external.analytics

interface CaramelAnalytics {
    fun logEvent(
        eventName: String,
        params: Map<String, Any>?,
    )

    fun setUserId(userId: String?)

    fun resetAnalyticsData()
}
