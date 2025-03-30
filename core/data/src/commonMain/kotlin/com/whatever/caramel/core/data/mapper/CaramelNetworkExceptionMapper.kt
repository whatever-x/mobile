package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.code.AppErrorCode
import com.whatever.caramel.core.domain.exception.code.CoupleErrorCode
import com.whatever.caramel.core.domain.exception.code.NetworkErrorCode
import com.whatever.caramel.core.domain.exception.code.UserErrorCode
import com.whatever.caramel.core.remote.network.exception.CaramelNetworkException

fun CaramelNetworkException.toCaramelException(): CaramelException {
    val appErrorCode = when (code) {
        NetworkErrorCode.INVITATION_CODE_EXPIRED -> CoupleErrorCode.EXPIRED_INVITATION_CODE
        NetworkErrorCode.INVALID_USER_STATUS -> UserErrorCode.INVALID_USER_STATUS
        else -> AppErrorCode.UNKNOWN
    }
    return CaramelException(
        code = appErrorCode,
        message = this.message,
        debugMessage = "통신 과정에서 오류가 발생했습니다. message를 확인해주세요."
    )
}