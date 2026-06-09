package com.whatever.caramel.di

import com.whatever.caramel.app.util.AnalyticsEventObserver
import com.whatever.caramel.core.domain.event.AnalyticsEventBus
import org.koin.core.module.Module
import org.koin.dsl.module

expect val appModule: Module

val analyticsEventObserverModule: Module =
    module {
        single {
            AnalyticsEventObserver(
                events = get<AnalyticsEventBus>().events,
                analytics = get(),
            )
        }
    }
