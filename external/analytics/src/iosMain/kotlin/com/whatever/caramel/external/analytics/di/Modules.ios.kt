package com.whatever.caramel.external.analytics.di

import com.whatever.caramel.external.analytics.CaramelAnalytics
import com.whatever.caramel.external.analytics.CaramelAnalyticsImpl
import org.koin.core.module.Module
import org.koin.dsl.module

actual val analyticsModule: Module =
    module {
        single<CaramelAnalytics> { CaramelAnalyticsImpl() }
    }
