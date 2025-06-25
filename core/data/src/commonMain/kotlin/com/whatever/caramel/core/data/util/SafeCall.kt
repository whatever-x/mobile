package com.whatever.caramel.core.data.util

import com.whatever.caramel.core.data.mapper.toCaramelException
import com.whatever.caramel.core.domain.exception.code.AppErrorCode
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.ErrorUiType
import com.whatever.caramel.core.domain.exception.code.NetworkErrorCode
import com.whatever.caramel.core.remote.network.exception.CaramelNetworkException

// @ham2174 TODO : 로컬 관련 커스텀 예외 추가시 catch 추가
suspend fun <T> safeCall(block: suspend () -> T): T =
    try {
        block.invoke()
    } catch (e: CaramelNetworkException) {
        throw e.toCaramelException()
    } catch (e: Exception) {
        throw CaramelException(
            code = NetworkErrorCode.UNKNOWN,
            message = "예상치 못한 에러가 발생했습니다.",
            debugMessage = e.message,
            errorUiType = ErrorUiType.DIALOG
        )
    }