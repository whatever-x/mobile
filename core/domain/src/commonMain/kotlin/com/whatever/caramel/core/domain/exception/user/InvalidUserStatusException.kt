package com.whatever.caramel.core.domain.exception.user

import com.whatever.caramel.core.domain.exception.code.UserErrorCode

data class InvalidUserStatusException(
    val code : String = UserErrorCode.INVALID_USER_STATUS,
    override val message : String
) : Exception(message)