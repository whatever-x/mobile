package com.whatever.caramel.core.domain.exception.code

data object UserErrorCode {
    private const val PREFIX = "USER"

    const val INVALID_USER_STATUS = PREFIX + "100"
    const val INVALID_NICKNAME_SIZE = PREFIX + "101"
    const val INVALID_NICKNAME_FORMAT = PREFIX + "102"
}