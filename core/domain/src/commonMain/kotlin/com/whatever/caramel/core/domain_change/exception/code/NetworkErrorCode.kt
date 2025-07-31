package com.whatever.caramel.core.domain_change.exception.code

data object NetworkErrorCode {
    private const val PREFIX = "GLOBAL"

    const val UNKNOWN = PREFIX + "000"
    const val NO_RESOURCE = PREFIX + "001"
    const val ARGS_VALIDATION_FAILED = PREFIX + "002"
    const val ARGS_TYPE_MISMATCH = PREFIX + "003"
    const val INVALID_ARGUMENT = PREFIX + "004"
    const val ILLEGAL_STATE = PREFIX + "005"
    const val ACCESS_DENIED = PREFIX + "006"
}
