package com.whatever.caramel.core.remote.network.config

import io.ktor.client.HttpClientConfig
import io.ktor.client.request.HttpRequestPipeline

expect fun currentTimeZone(): String

fun HttpClientConfig<*>.addTimeZoneHeader() {
    install("AddTimeZoneHeader") {
        requestPipeline.intercept(HttpRequestPipeline.State) {
            context.headers.append(Header.TIME_ZONE, currentTimeZone())
            proceed()
        }
    }
}
