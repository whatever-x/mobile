package com.whatever.caramel.core.data

import com.whatever.caramel.BuildConfig

actual object NetworkConfig {
    actual val BASE_URL: String = BuildConfig.BASE_URL
    actual val isDebug: Boolean = BuildConfig.DEBUG
}