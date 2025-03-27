package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.remote.network.exception.CaramelNetworkException

// @ham2174 TODO : 에러 코드에 따른 ErrorUiType 설정
fun CaramelNetworkException.toCaramelException(): CaramelException =
    CaramelException(
        code = this.code,
        message = this.message,
        debugMessage = this.debugMessage,
    )
