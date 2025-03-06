package com.whatever.caramel.core.data.remote.network

import com.whatever.caramel.core.data.remote.dto.response.BaseResponse
import com.whatever.caramel.core.data.remote.dto.response.ErrorResponse
import com.whatever.caramel.core.data.remote.network.exception.CaramelNetworkException
import com.whatever.caramel.core.data.remote.network.interceptor.TokenInterceptor
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
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
        tokenInterceptor: TokenInterceptor
    ): HttpClient {
        return HttpClient(engine) {
            expectSuccess = true

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
                level = if (NetworkConfig.isDebug) LogLevel.ALL else LogLevel.NONE
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(
                            accessToken = tokenInterceptor.getAccessToken(),
                            refreshToken = tokenInterceptor.getRefreshToken()
                        )
                    }

                    refreshTokens {
                        tokenInterceptor.refreshAccessToken()
                        BearerTokens(
                            accessToken = tokenInterceptor.getAccessToken(),
                            refreshToken = tokenInterceptor.getRefreshToken()
                        )
                    }
                }
            }
            HttpResponseValidator {
                handleResponseExceptionWithRequest { exception, _ ->
                    val clientException = exception as ResponseException
                    val exceptionResponse = clientException.response
                    val baseResponse =
                        try {
                            exceptionResponse.body<BaseResponse<Unit>>()
                        } catch (e: Exception) {
                            BaseResponse<Unit>(
                                success = false,
                                data = null,
                                error = ErrorResponse(
                                    code = clientException.response.status.value.toString(),
                                    message = "예상치 못한 에러 발생",
                                    debugMessage =
                                        "Error Code : ${clientException.response.status}\n"
                                                + "Error Message : ${clientException.message}",
                                )
                            )
                        }

                    throw CaramelNetworkException(
                        baseResponse.error?.code!!,
                        baseResponse.error.debugMessage,
                        baseResponse.error.message
                    )
                }
            }
            defaultRequest {
                url(NetworkConfig.BASE_URL)
                contentType(ContentType.Application.Json)
            }
        }
    }
}
