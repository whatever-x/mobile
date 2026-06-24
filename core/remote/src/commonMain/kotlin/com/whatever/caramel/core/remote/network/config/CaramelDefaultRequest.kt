package com.whatever.caramel.core.remote.network.config

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.DefaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType

internal fun HttpClientConfig<*>.caramelDefaultRequest(baseUrl: String) {
    install(DefaultRequest) {
        url(baseUrl)
        contentType(ContentType.Application.Json)
    }
}
