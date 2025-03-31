package com.whatever.caramel.core.domain.exception.code

data object AuthErrorCode {
    private const val PREFIX = "AUTH"

    const val LOGIN_FAILED = PREFIX + "100"
    const val LOGIN_CANCELLED = PREFIX + "101"
}