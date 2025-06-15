package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.ErrorUiType
import com.whatever.caramel.core.remote.network.exception.CaramelNetworkException

fun CaramelNetworkException.toCaramelException(): CaramelException {
    return CaramelException(
        code = this.code,
        message = this.message,
        debugMessage = "통신 과정에서 오류가 발생했습니다. message를 확인해주세요.",
        errorUiType = kotlin.runCatching { ErrorUiType.valueOf(this.errorUiType) }
            .getOrElse { ErrorUiType.DIALOG }
    )
}