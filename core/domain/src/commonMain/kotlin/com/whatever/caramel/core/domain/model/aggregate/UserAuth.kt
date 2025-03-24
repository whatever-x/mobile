package com.whatever.caramel.core.domain.model.aggregate

import com.whatever.caramel.core.domain.entity.user.UserBasic
import com.whatever.caramel.core.domain.model.auth.AuthToken

data class UserAuth(
    val authToken : AuthToken,
    val userBasic : UserBasic
)