package com.whatever.caramel.core.data.util

import com.whatever.caramel.core.crashlytics.getCaramelCrashlytics
import com.whatever.caramel.core.data.mapper.toCaramelException
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.ErrorUiType
import com.whatever.caramel.core.domain.exception.code.NetworkErrorCode
import com.whatever.caramel.core.remote.network.exception.CaramelNetworkException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.io.IOException

// @ham2174 TODO : 로컬 관련 커스텀 예외 추가시 catch 추가
suspend fun <T> safeCall(block: suspend () -> T): T =
    try {
        block.invoke()
    } catch (e: Exception) {
        throw e.toCaramelExceptionWithReporting()
    }

private fun Throwable.toCaramelExceptionWithReporting(): CaramelException =
    when (this) {
        is CaramelNetworkException -> this.toCaramelException()
        is IOException ->
            CaramelException(
                code = NetworkErrorCode.UNKNOWN,
                message = "네트워크 오류가 발생했습니다.",
                debugMessage = message,
                errorUiType = ErrorUiType.DIALOG,
            )
        is TimeoutCancellationException ->
            CaramelException(
                code = NetworkErrorCode.UNKNOWN,
                message = "네트워크 요청이 시간 초과되었습니다.",
                debugMessage = message,
                errorUiType = ErrorUiType.DIALOG,
            )
        else -> {
            getCaramelCrashlytics().run {
                log("Repository 호출중 예상치 못한 오류 발생")
                recordException(this@toCaramelExceptionWithReporting)
            }
            CaramelException(
                code = NetworkErrorCode.UNKNOWN,
                message = "예상치 못한 에러가 발생했습니다.",
                debugMessage = message,
                errorUiType = ErrorUiType.DIALOG,
            )
        }
    }
