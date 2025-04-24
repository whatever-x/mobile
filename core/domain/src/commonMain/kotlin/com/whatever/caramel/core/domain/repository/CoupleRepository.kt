package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.entity.Couple
import com.whatever.caramel.core.domain.vo.couple.CoupleInvitationCode
import com.whatever.caramel.core.domain.vo.couple.CoupleRelationship

interface CoupleRepository {
    suspend fun getCoupleInvitationCode() : CoupleInvitationCode
    suspend fun connectCouple(invitationCode: String) : CoupleRelationship
    suspend fun setCoupleId(coupleId: Long)
    suspend fun getCoupleId() : Long
    suspend fun getCoupleInfo(coupleId : Long) : CoupleRelationship
    suspend fun updateShareMessage(
        coupleId: Long,
        shareMessage: String
    ) : Couple
}