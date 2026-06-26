package com.whatever.caramel.core.remote.di

import android.content.Context
import android.content.pm.ApplicationInfo
import com.whatever.caramel.core.remote.BuildKonfig
import com.whatever.caramel.core.remote.network.config.AndroidDeviceIdProvider
import com.whatever.caramel.core.remote.network.config.DeviceIdProvider
import com.whatever.caramel.core.remote.network.config.NetworkConfig
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

actual val networkConfigModule: Module
    get() =
        module {
            single {
                val context = get<Context>()

                NetworkConfig(
                    baseUrl = BuildKonfig.CARAMEL_BASE_URL,
                    isDebug = (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0,
                )
            }
        }
