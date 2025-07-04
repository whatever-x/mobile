package com.whatever.caramel.core.remote.network.config

import com.whatever.caramel.core.remote.BuildConfig

actual object NetworkConfig {
    actual val BASE_URL: String = BuildConfig.BASE_URL
    actual val SAMPLE_URL: String = BuildConfig.SAMPLE_URL
    actual val isDebug: Boolean = BuildConfig.DEBUG
}
