package com.whatever.caramel.core.domain.exception.code

data object AuthErrorCode {
    private const val PREFIX = "AUTH"

    const val LOGIN_FAILED = PREFIX + "100"
    const val LOGIN_CANCELLED = PREFIX + "101"
    const val EMPTY_ID_TOKEN = PREFIX + "102"
    const val EMPTY_AUTH_TOKEN = PREFIX + "103"
}