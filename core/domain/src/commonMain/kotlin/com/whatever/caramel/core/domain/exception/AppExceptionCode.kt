package com.whatever.caramel.core.domain.exception

data object AppExceptionCode {
    private const val APP_BASE_PREFIX = "APP"
    const val INVALID_PARAMS = APP_BASE_PREFIX + "000"
    const val EMPTY_VALUE = APP_BASE_PREFIX + "001"
    const val UNKNOWN = APP_BASE_PREFIX + "999"
}