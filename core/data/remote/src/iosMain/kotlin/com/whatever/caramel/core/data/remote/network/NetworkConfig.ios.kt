package com.whatever.caramel.core.data.remote.network

import platform.Foundation.NSBundle
import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
actual object NetworkConfig {
    actual val BASE_URL: String = NSBundle.mainBundle.objectForInfoDictionaryKey("CaramelBaseUrl") as String
    actual val isDebug: Boolean = Platform.isDebugBinary
}