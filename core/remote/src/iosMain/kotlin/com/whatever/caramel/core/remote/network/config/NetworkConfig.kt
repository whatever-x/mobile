package com.whatever.caramel.core.remote.network.config

import platform.Foundation.NSBundle
import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
actual object NetworkConfig {
    actual val BASE_URL: String = NSBundle.mainBundle.objectForInfoDictionaryKey("CaramelBaseUrl") as String
    actual val SAMPLE_URL: String = NSBundle.mainBundle.objectForInfoDictionaryKey("CaramelSampleUrl") as String
    actual val isDebug: Boolean = Platform.isDebugBinary
}