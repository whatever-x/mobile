package com.whatever.caramel.core.data.mapper

import com.whatever.caramel.core.domain.model.couple.CoupleInvitationCode
import com.whatever.caramel.core.remote.dto.couple.CoupleInvitationCodeResponse
import kotlinx.datetime.Instant

fun CoupleInvitationCodeResponse.toCoupleInvitationCode() = CoupleInvitationCode(
    invitationCode = this.invitationCode,
    expirationMillisecond = Instant.parse(expirationDateTime).toEpochMilliseconds()
)