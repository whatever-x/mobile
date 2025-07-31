package com.whatever.caramel.core.domain_change.policy

/**
 * 도메인 성격을 가진 정책
 * */
data object UserPolicy {
    const val NICKNAME_MIN_LENGTH = 2
    const val NICKNAME_MAX_LENGTH = 8

    val NICKNAME_PATTERN = Regex("^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣]*\$")
}