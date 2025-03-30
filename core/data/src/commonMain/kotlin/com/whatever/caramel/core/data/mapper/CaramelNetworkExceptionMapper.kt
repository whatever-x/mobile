package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.remote.network.exception.CaramelNetworkException

fun CaramelNetworkException.toCaramelException(): CaramelException {
    return CaramelException(
        code = this.code,
        message = this.message,
        debugMessage = this.message
    )
}