package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.entity.Couple
import com.whatever.caramel.core.domain.vo.couple.Anniversary
import com.whatever.caramel.core.domain.vo.couple.CoupleInvitationCode
import com.whatever.caramel.core.domain.vo.couple.CoupleRelationship

interface CoupleRepository {
    suspend fun getCoupleInvitationCode() : CoupleInvitationCode
    suspend fun connectCouple(invitationCode: String) : CoupleRelationship
    suspend fun setCoupleId(coupleId: Long)
    suspend fun getCoupleId() : Long
    suspend fun getCoupleRelationshipInfo(coupleId : Long) : CoupleRelationship
    suspend fun editCoupleStartDate(coupleId : Long, startDate : String) : Couple
    suspend fun updateShareMessage(
        coupleId: Long,
        shareMessage: String
    ) : Couple
    suspend fun getCoupleInfo() : Couple
    suspend fun getAnniversaries(coupleId : Long, startDate : String, endDate : String) : List<Anniversary>
    suspend fun deleteCoupleId()
}