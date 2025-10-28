package com.whatever.caramel.core.domain.event

import kotlinx.coroutines.flow.Flow

interface AnalyticsEventBus {

    val events: Flow<AnalyticsEvent>

    suspend fun emit(event: AnalyticsEvent)

}