package com.whatever.caramel.core.domain.vo.user

import com.whatever.caramel.core.domain.vo.auth.AuthToken

data class RefreshUserSessionResult(
    val userId: Long,
    val userStatus: UserStatus,
    val authToken: AuthToken,
)