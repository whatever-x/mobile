package com.whatever.caramel.core.domain.exception.code

data object CoupleErrorCode {
    private const val PREFIX = "COUPLE"

    const val INVALID_USER_STATUS = PREFIX + "001"
    const val INVITATION_CODE_GENERATION_FAIL = PREFIX + "002"
    const val INVITATION_CODE_EXPIRED = PREFIX + "003"
    const val INVITATION_CODE_SELF_GENERATED = PREFIX + "004"
    const val MEMBER_NOT_FOUND = PREFIX + "005"
    const val COUPLE_NOT_FOUND = PREFIX + "006"
    const val NOT_A_MEMBER = PREFIX + "007"
    const val ILLEGAL_MEMBER_SIZE = PREFIX + "008"
    const val UPDATE_FAIL = PREFIX + "009"
}