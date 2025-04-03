package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.entity.Couple
import com.whatever.caramel.core.domain.vo.couple.CoupleInvitationCode

interface CoupleRepository {
    suspend fun getCoupleInvitationCode() : CoupleInvitationCode
    suspend fun connectCouple(invitationCode: String) : Couple
}