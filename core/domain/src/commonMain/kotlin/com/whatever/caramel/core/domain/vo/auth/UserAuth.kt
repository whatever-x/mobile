package com.whatever.caramel.core.domain.vo.auth

import com.whatever.caramel.core.domain.vo.user.UserStatus

data class UserAuth(
    val coupleId: Long? = null,
    val userStatus: UserStatus,
    val nickname: String? = null,
    val birthday: String? = null,
    val authToken: AuthToken,
)
