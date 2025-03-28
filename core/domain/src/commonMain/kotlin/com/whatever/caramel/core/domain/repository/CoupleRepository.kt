package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.model.couple.CoupleInvitationCode

interface CoupleRepository {
    suspend fun getCoupleInvitationCode() : CoupleInvitationCode
    suspend fun connectCouple(invitationCode: String)
}