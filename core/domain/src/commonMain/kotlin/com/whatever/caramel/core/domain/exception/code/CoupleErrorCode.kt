package com.whatever.caramel.core.domain.exception.code

data object CoupleErrorCode {
    private const val PREFIX = "COUPLE"

    const val INVALID_INVITATION_CODE = PREFIX + "100"
    const val EXPIRED_INVITATION_CODE = PREFIX + "101"
}