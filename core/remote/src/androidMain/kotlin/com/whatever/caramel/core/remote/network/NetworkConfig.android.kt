package com.whatever.caramel.core.remote.network

import com.whatever.caramel.core.remote.BuildConfig

actual object NetworkConfig {
    actual val BASE_URL: String = BuildConfig.BASE_URL
    actual val isDebug: Boolean = BuildConfig.DEBUG
}
