package com.whatever.caramel.core.domain.exception.code

data object AppErrorCode {
    private const val APP_BASE_PREFIX = "APP"

    const val INVALID_PARAMS = APP_BASE_PREFIX + "100"
    const val EMPTY_VALUE = APP_BASE_PREFIX + "101"
    const val NULL_VALUE = APP_BASE_PREFIX + "102"

    const val UNKNOWN = APP_BASE_PREFIX + "999"
}