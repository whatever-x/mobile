package com.whatever.caramel.core.domain.policy

data object UserPolicy {
    const val NICKNAME_MIN_LENGTH = 2
    const val NICKNAME_MAX_LENGTH = 8

    val NICKNAME_PATTERN = Regex("^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣]*\$")
}