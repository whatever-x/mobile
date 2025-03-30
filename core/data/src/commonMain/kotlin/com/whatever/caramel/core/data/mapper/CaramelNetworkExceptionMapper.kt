package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.code.NetworkErrorCode
import com.whatever.caramel.core.domain.exception.couple.ExpiredInvitationCodeException
import com.whatever.caramel.core.domain.exception.user.InvalidUserStatusException
import com.whatever.caramel.core.remote.network.exception.CaramelNetworkException

fun CaramelNetworkException.toCaramelException(): Exception = when(code) {
    NetworkErrorCode.INVITATION_CODE_EXPIRED -> ExpiredInvitationCodeException(message = this.message)
    NetworkErrorCode.INVALID_USER_STATUS -> InvalidUserStatusException(message = this.message)
    else -> CaramelException(message = this.message, code = NetworkErrorCode.UNKNOWN)
}
