package com.whatever.caramel.core.remote.network.config

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.DefaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType

internal fun HttpClientConfig<*>.caramelDefaultRequest() {
    install(DefaultRequest) {
        url(NetworkConfig.BASE_URL)
        contentType(ContentType.Application.Json)
    }
}
