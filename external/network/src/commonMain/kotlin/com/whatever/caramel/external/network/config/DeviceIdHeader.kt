package com.whatever.caramel.external.network.config

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.defaultRequest

interface DeviceIdProvider {
    val deviceId: String
}

fun HttpClientConfig<*>.addDeviceIdHeader(deviceIdProvider: DeviceIdProvider) {
    defaultRequest {
        headers.append("Device-Id", deviceIdProvider.deviceId)
    }
}
