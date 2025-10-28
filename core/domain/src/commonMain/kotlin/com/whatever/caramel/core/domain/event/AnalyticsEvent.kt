package com.whatever.caramel.core.domain.event

sealed interface AnalyticsEvent {

    data class SetUserId(
        val id: String
    ) : AnalyticsEvent

    data class LogEvent(
        val eventName: String,
        val params: Map<String, Any>?
    ) : AnalyticsEvent

    data object ResetAnalyticsData : AnalyticsEvent

}