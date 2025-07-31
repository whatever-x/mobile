package com.whatever.caramel.core.domain_change.exception.code

data object ContentErrorCode {
    private const val PREFIX = "Content"

    const val CONTENT_NOT_FOUND = PREFIX + "005"

    const val MAX_LENGTH_TITLE = PREFIX + "1001"
    const val CAN_NOT_CHANGE_OF_LINE = PREFIX + "1002"
    const val MAX_LENGTH_BODY = PREFIX + "1003"
}
