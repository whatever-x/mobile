package com.whatever.caramel.core.remote.network.config

data class NetworkConfig(
    val baseUrl: String,
    val isDebug: Boolean,
)

data object Header {
    const val TIME_ZONE = "Time-Zone"
}
