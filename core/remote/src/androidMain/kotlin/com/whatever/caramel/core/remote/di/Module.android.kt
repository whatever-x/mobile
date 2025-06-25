package com.whatever.caramel.core.remote.di

import com.whatever.caramel.core.remote.network.config.AndroidDeviceIdProvider
import com.whatever.caramel.core.remote.network.config.DeviceIdProvider
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val networkClientEngineModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
    }

actual val deviceIdModule: Module
    get() = module {
        single<DeviceIdProvider> {
            AndroidDeviceIdProvider(get())
        }
    }