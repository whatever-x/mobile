package com.whatever.caramel.external.network.di

import com.whatever.caramel.external.network.config.DeviceIdProvider
import com.whatever.caramel.external.network.config.AndroidDeviceIdProvider
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val networkClientEngineModule: Module
    get() =
        module {
            single<HttpClientEngine> { OkHttp.create() }
        }

actual val deviceIdModule: Module
    get() =
        module {
            single<DeviceIdProvider> {
                AndroidDeviceIdProvider(get())
            }
        }
