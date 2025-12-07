package com.whatever.caramel.external.crashlytics.di

import com.whatever.caramel.external.crashlytics.CaramelCrashlytics
import com.whatever.caramel.external.crashlytics.CaramelCrashlyticsImpl
import firebaseCrashlyticsBridge.FBCrashlyticsBridge
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module

@OptIn(ExperimentalForeignApi::class)
actual val crashlyticsModule: Module
    get() =
        module {
            single<CaramelCrashlytics> {
                CaramelCrashlyticsImpl(
                    firebaseCrashlytics = FBCrashlyticsBridge(),
                )
            }
        }
