package com.whatever.caramel.core.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(
        engine: HttpClientEngine,
        baseUrl: String
    ): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                    }
                )
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 30_000
                connectTimeoutMillis = 10_000
                socketTimeoutMillis = 10_000
            }
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
            defaultRequest {
                url(baseUrl)
                contentType(ContentType.Application.Json)
            }
        }
    }
}
