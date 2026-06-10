package com.whatever.caramel.core.remote.network.config

import com.whatever.caramel.core.remote.BuildKonfig

actual object NetworkConfig {
    actual val BASE_URL: String = BuildKonfig.BASE_URL
    actual val isDebug: Boolean = BuildKonfig.DEBUG
}
