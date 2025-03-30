package com.whatever.caramel.core.domain.exception.couple

import com.whatever.caramel.core.domain.exception.code.CoupleErrorCode

data class ExpiredInvitationCodeException(
    val code : String = CoupleErrorCode.EXPIRED_INVITATION_CODE,
    override val message: String
) : Exception(message)