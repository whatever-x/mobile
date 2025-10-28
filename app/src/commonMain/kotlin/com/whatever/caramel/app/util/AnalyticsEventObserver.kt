package com.whatever.caramel.app.util

import com.whatever.caramel.core.analytics.CaramelAnalytics
import com.whatever.caramel.core.domain.event.AnalyticsEvent
import kotlinx.coroutines.flow.Flow

class AnalyticsEventObserver(
    private val events: Flow<AnalyticsEvent>,
    private val analytics: CaramelAnalytics
) {
    suspend fun observeEvent() {
        events.collect { event ->
            when (event) {
                is AnalyticsEvent.SetUserId -> analytics.setUserId(userId = event.id)
                is AnalyticsEvent.LogEvent -> analytics.logEvent(
                    eventName = event.eventName,
                    params = null
                )
                is AnalyticsEvent.ResetAnalyticsData -> analytics.resetAnalyticsData()
            }
        }
    }
}