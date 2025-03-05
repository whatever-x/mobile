package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.data.remote.dto.response.ErrorResponse
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.ErrorUiType

// @ham2174 TODO : 추후 ErrorResponse 에 [flag] 필드 추가에 따른 ErrorUiType 변경 예정
fun ErrorResponse.toCaramelException(): CaramelException =
    CaramelException(
        message = this.message,
        debugMessage = this.debugMessage,
        errorUiType = when (this.code) {
            "S001" -> ErrorUiType.SNACK_BAR
            "E001" -> ErrorUiType.EMPTY_UI
            else -> ErrorUiType.SNACK_BAR
        }
    )