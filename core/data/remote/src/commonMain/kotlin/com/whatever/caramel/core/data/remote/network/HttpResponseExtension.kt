package com.whatever.caramel.core.data.remote.network

import com.whatever.caramel.core.data.remote.dto.response.BaseResponse
import com.whatever.caramel.core.data.remote.mapper.toCaramelException
import com.whatever.caramel.core.domain.CaramelException
import com.whatever.caramel.core.domain.ErrorUiType
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend inline fun <reified T> HttpResponse.getBody(): T {
    val baseResponse = this.body<BaseResponse<T>>()

    if (baseResponse.success && baseResponse.data != null) {
        return baseResponse.data
    } else {
        if (baseResponse.error != null) {
            throw baseResponse.error.toCaramelException()
        } else {
            throw CaramelException(
                message = "Unknown Error",
                debugMessage = "Unknown Error",
                errorUiType = ErrorUiType.EMPTY_UI
            )
        }
    }
}
