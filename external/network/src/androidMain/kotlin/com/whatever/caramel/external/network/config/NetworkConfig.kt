package com.whatever.caramel.external.network.config

import com.whatever.caramel.external.network.BuildConfig

actual object NetworkConfig {
    actual val BASE_URL: String = BuildConfig.BASE_URL
    actual val isDebug: Boolean = BuildConfig.DEBUG
}
