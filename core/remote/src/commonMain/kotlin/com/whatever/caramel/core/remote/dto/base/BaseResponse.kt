package com.whatever.caramel.core.remote.dto.base

import com.whatever.caramel.core.remote.network.exception.CaramelNetworkException
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("success") val success: Boolean,
    @SerialName("data") val data: T?,
    @SerialName("error") val error: ErrorResponse?
)

// @ham2174 TODO : 회의 이후 ErrorResponse 필드 변경 예정
@Serializable
data class ErrorResponse(
    @SerialName("code") val code: String,
    @SerialName("message") val message: String,
    @SerialName("debugMessage") val debugMessage: String?,
    @SerialName("description") val description: String?,
    @SerialName("errorUiType") val errorUiType: String,
) {
    fun toException() : CaramelNetworkException {
        return CaramelNetworkException(
            code = this.code,
            message = this.message,
            debugMessage = this.debugMessage,
            description = this.description,
            errorUiType = this.errorUiType
        )
    }
}
