package com.whatever.caramel.core.domain.exception.code

data object AppExceptionCode {
    private const val APP_BASE_PREFIX = "APP"
    /* data */
    const val INVALID_PARAMS = APP_BASE_PREFIX + "000"
    const val EMPTY_VALUE = APP_BASE_PREFIX + "001"
    const val NULL_VALUE = APP_BASE_PREFIX + "002"

    /* feature */
    const val LOGIN_FAILED = APP_BASE_PREFIX + "100"
    const val LOGIN_CANCELLED = APP_BASE_PREFIX + "101"

    const val UNKNOWN = APP_BASE_PREFIX + "999"
}