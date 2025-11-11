package com.whatever.caramel.core.analytics.di

import com.whatever.caramel.core.analytics.CaramelAnalytics
import com.whatever.caramel.core.analytics.CaramelAnalyticsImpl
import org.koin.core.module.Module
import org.koin.dsl.module

actual val analyticsModule: Module =
    module {
        single<CaramelAnalytics> { CaramelAnalyticsImpl() }
    }
