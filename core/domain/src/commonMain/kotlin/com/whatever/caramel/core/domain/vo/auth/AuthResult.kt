package com.whatever.caramel.core.domain.vo.auth

import com.whatever.caramel.core.domain.vo.user.UserStatus

data class AuthResult(
    val coupleId: Long? = null,
    val userId: Long,
    val userStatus: UserStatus,
    val authToken: AuthToken,
)
