package com.whatever.caramel.core.remote.di

import com.whatever.caramel.core.remote.network.config.DeviceIdProvider
import com.whatever.caramel.core.remote.network.config.IOSDeviceIdProvider
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val networkClientEngineModule: Module
    get() =
        module {
            single<HttpClientEngine> { Darwin.create() }
        }

actual val deviceIdModule: Module
    get() =
        module {
            single<DeviceIdProvider> {
                IOSDeviceIdProvider()
            }
        }
