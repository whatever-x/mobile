package com.whatever.caramel.core.domain.vo.user

data class UserAgreement(
    val serviceTerms: Boolean = false,
    val privacyPolicy: Boolean = false,
)
