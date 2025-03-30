package com.whatever.caramel.core.domain.exception.couple

import com.whatever.caramel.core.domain.exception.code.CoupleErrorCode

data class InvalidInvitationCodeException(
    val code : String = CoupleErrorCode.INVALID_INVITATION_CODE,
    override val message: String
) : Exception(message)