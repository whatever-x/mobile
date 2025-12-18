package com.whatever.caramel.external.network.util

import com.whatever.caramel.core.datasource.remote.dto.base.BaseResponse
import com.whatever.caramel.core.datasource.remote.exception.CaramelNetworkException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

/**
 * API 통신 시 발생하는 오류에 대한 예외처리
 * @return API에 대한 DTO, DTO가 Unit, Response가 null인 경우 Unit을 return
 * @throws CaramelNetworkException API 통신은 성공했으나 서버에서 오류가 나온 경우
 * */
suspend inline fun <reified T> HttpResponse.getBody(): T {
    val baseResponse = this.body<BaseResponse<T>>()
    if (baseResponse.success) {
        return baseResponse.data ?: if (T::class == Unit::class) {
            Unit as T
        } else {
            throw CaramelNetworkException(
                code = "Unknown",
                message = "예상치 못한 에러가 발생했습니다.",
                debugMessage = "Data is null",
                description = null,
                errorUiType = "DIALOG",
            )
        }
    } else {
        throw baseResponse.error?.toException() ?: throw CaramelNetworkException(
            code = "Unknown",
            message = "Unknown Error",
            debugMessage = "Unknown Error",
            description = null,
            errorUiType = "DIALOG",
        )
    }
}
