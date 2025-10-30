package com.whatever.caramel.core.domain.event

typealias AnalyticsEventParams = Map<String, Any>?

sealed interface AnalyticsEvent {
    interface Loggable : AnalyticsEvent {
        val eventName: String
        val params: AnalyticsEventParams
    }
}

sealed class AnalyticsLogEvent(
    final override val eventName: String,
    final override val params: AnalyticsEventParams,
) : AnalyticsEvent.Loggable {
    data object RequestedInAppReview : AnalyticsLogEvent(
        eventName = "requested_in_app_review",
        params = null,
    )
}

sealed interface AnalyticsUserLifecycleEvent : AnalyticsEvent {
    data class RefreshedUserSession(
        val userId: String,
    ) : AnalyticsUserLifecycleEvent

    data object SignOuted : AnalyticsUserLifecycleEvent

    data object LogOuted : AnalyticsUserLifecycleEvent
}
