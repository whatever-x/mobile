package com.whatever.caramel.core.domain_change.exception.code

data object UserErrorCode {
    private const val PREFIX = "USER"

    const val INVALID_NICKNAME_LENGTH = PREFIX + "001"
    const val INVALID_NICKNAME_CHARACTER = PREFIX + "002"
    const val NICKNAME_REQUIRED = PREFIX + "003"
    const val SERVICE_TERMS_AGREEMENT_REQUIRED = PREFIX + "005"
    const val PRIVATE_POLICY_AGREEMENT_REQUIRED = PREFIX + "006"
    const val ALREADY_EXIST_COUPLE = PREFIX + "007" // 이미 커플이 존재하는 유저
}
