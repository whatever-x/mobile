package com.whatever.caramel.core.domain.exception.code

data object NetworkErrorCode {
    /* NETWORK(GLOBAL) */
    private const val NETWORK_GLOBAL_PREFIX = "GLOBAL"
    const val UNKNOWN = NETWORK_GLOBAL_PREFIX + "000"

    /* COUPLE */
    private const val COUPLE_PREFIX = "COUPLE"
    const val INVALID_USER_STATUS = COUPLE_PREFIX + "001"
    const val INVITATION_CODE_GENERATION_FAIL = COUPLE_PREFIX + "002"
    const val INVITATION_CODE_EXPIRED = COUPLE_PREFIX + "003"
    const val INVITATION_CODE_SELF_GENERATED = COUPLE_PREFIX + "004"
    const val MEMBER_NOT_FOUND = COUPLE_PREFIX + "005"
    const val COUPLE_NOT_FOUND = COUPLE_PREFIX + "006"
    const val NOT_A_MEMBER = COUPLE_PREFIX + "007"
    const val ILLEGAL_MEMBER_SIZE = COUPLE_PREFIX + "008"
    const val UPDATE_FAIL = COUPLE_PREFIX + "009"
}