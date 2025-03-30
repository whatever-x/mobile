package com.whatever.caramel.core.domain.exception.code

object UserErrorCode {
    private const val PREFIX = "USER"

    const val INVALID_USER_STATUS = PREFIX + "001"
    const val INVALID_NICKNAME_SIZE = PREFIX + "002"
    const val INVALID_NICKNAME_FORMAT = PREFIX + "003"
}