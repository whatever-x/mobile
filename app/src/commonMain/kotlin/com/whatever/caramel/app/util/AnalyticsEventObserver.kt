package com.whatever.caramel.app.util

import com.whatever.caramel.core.analytics.CaramelAnalytics
import com.whatever.caramel.core.domain.event.AnalyticsEvent
import com.whatever.caramel.core.domain.event.AnalyticsUserLifecycleEvent
import kotlinx.coroutines.flow.Flow

class AnalyticsEventObserver(
    private val events: Flow<AnalyticsEvent>,
    private val analytics: CaramelAnalytics,
) {
    suspend fun observeEvent() {
        events.collect { event ->
            when (event) {
                is AnalyticsEvent.Loggable ->
                    analytics.logEvent(
                        eventName = event.eventName,
                        params = event.params,
                    )
                is AnalyticsUserLifecycleEvent.RefreshedUserSession -> analytics.setUserId(userId = event.userId)
                is AnalyticsUserLifecycleEvent.LogOuted,
                is AnalyticsUserLifecycleEvent.SignOuted,
                -> analytics.resetAnalyticsData()
            }
        }
    }
}
