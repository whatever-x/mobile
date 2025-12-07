package com.whatever.caramel.external.crashlytics.di

import com.whatever.caramel.external.crashlytics.CaramelCrashlytics
import com.whatever.caramel.external.crashlytics.CaramelCrashlyticsImpl
import org.koin.core.module.Module
import org.koin.dsl.module

actual val crashlyticsModule: Module
    get() =
        module {
            single<CaramelCrashlytics> {
                CaramelCrashlyticsImpl()
            }
        }
