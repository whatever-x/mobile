package com.whatever.caramel.core.data.remote.network

import com.whatever.caramel.core.data.remote.BuildConfig

actual object NetworkConfig {
    actual val BASE_URL: String = BuildConfig.BASE_URL
    actual val isDebug: Boolean = BuildConfig.DEBUG
}
