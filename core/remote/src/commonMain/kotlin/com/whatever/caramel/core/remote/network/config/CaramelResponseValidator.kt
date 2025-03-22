package com.whatever.caramel.core.remote.network.config

import com.whatever.caramel.core.remote.dto.base.BaseResponse
import com.whatever.caramel.core.remote.dto.base.ErrorResponse
import com.whatever.caramel.core.remote.network.exception.CaramelNetworkException
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpCallValidator
import io.ktor.client.plugins.ResponseException

internal fun HttpClientConfig<*>.caramelResponseValidator() {
    install(HttpCallValidator) {
        handleResponseExceptionWithRequest { exception, _ ->
            if (exception is ResponseException) {
                val exceptionResponse = exception.response
                val baseResponse =
                    try {
                        exceptionResponse.body<BaseResponse<Unit>>()
                    } catch (e: Exception) {
                        BaseResponse<Unit>(
                            success = false,
                            data = null,
                            error = ErrorResponse(
                                code = exception.response.status.value.toString(),
                                message = "예상치 못한 에러 발생",
                                debugMessage =
                                    "Error Code : ${exception.response.status}\n"
                                            + "Error Message : ${exception.message}",
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
    }
}