package com.whatever.caramel.core.data.setting

import com.whatever.caramel.core.data.BuildConfig

actual object NetworkConfig {
    actual val BASE_URL: String = BuildConfig.BASE_URL
    actual val isDebug: Boolean = BuildConfig.DEBUG
}