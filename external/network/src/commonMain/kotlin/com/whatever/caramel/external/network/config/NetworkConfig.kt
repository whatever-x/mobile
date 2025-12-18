package com.whatever.caramel.external.network.config

expect object NetworkConfig {
    val BASE_URL: String
    val isDebug: Boolean
}

data object Header {
    const val TIME_ZONE = "Time-Zone"
}
