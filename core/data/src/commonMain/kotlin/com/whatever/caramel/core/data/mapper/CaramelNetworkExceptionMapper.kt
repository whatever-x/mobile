package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.ErrorUiType
import com.whatever.caramel.core.remote.network.exception.CaramelNetworkException

// @ham2174 TODO : 에러 코드에 따른 ErrorUiType 설정
fun CaramelNetworkException.toCaramelException(): CaramelException =
    CaramelException(
        message = this.message,
        debugMessage = this.debugMessage,
        errorUiType = when (this.code) {
            "S001" -> ErrorUiType.SNACK_BAR
            "E001" -> ErrorUiType.EMPTY_UI
            else -> ErrorUiType.SNACK_BAR
        }
    )
