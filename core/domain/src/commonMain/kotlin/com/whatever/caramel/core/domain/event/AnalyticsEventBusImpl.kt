package com.whatever.caramel.core.domain.event

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AnalyticsEventBusImpl : AnalyticsEventBus {
    private val _events = MutableSharedFlow<AnalyticsEvent>(
        replay = 0,
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val events: SharedFlow<AnalyticsEvent> = _events.asSharedFlow()

    override suspend fun emit(event: AnalyticsEvent) {
        _events.emit(value = event)
    }
}