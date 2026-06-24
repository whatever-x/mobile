package com.whatever.caramel.core.remote.di

import com.whatever.caramel.core.remote.network.config.DeviceIdProvider
import com.whatever.caramel.core.remote.network.config.IOSDeviceIdProvider
import com.whatever.caramel.core.remote.network.config.NetworkConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import keychainHelperBridge.KeychainHelperBridge
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSBundle
import kotlin.experimental.ExperimentalNativeApi

actual val networkClientEngineModule: Module
    get() =
        module {
            single<HttpClientEngine> { Darwin.create() }
        }

@OptIn(ExperimentalForeignApi::class)
actual val deviceIdModule: Module
    get() =
        module {
            single<DeviceIdProvider> {
                IOSDeviceIdProvider(
                    keychainHelperBridge = KeychainHelperBridge(),
                )
            }
        }

@OptIn(ExperimentalNativeApi::class)
actual val networkConfigModule: Module
    get() =
        module {
            single {
                NetworkConfig(
                    baseUrl = NSBundle.mainBundle.objectForInfoDictionaryKey("CaramelBaseUrl") as String,
                    isDebug = Platform.isDebugBinary,
                )
            }
        }
