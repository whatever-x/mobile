package com.whatever.caramel.core.data

import platform.Foundation.NSBundle

actual object NetworkConfig {
    actual val BASE_URL: String
        get() = NSBundle.mainBundle.objectForInfoDictionaryKey("BaseUrl") as? String
            ?: error("Network Config Error - CaramelBaseUrl Error")
}