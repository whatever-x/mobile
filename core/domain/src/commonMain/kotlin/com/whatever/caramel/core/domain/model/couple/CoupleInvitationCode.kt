package com.whatever.caramel.core.domain.model.couple

import kotlinx.datetime.LocalDateTime

data class CoupleInvitationCode(
    val invitationCode: String,
    val expirationMillisecond: Long
)