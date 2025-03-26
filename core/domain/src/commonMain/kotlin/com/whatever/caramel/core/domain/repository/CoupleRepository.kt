package com.whatever.caramel.core.domain.repository

interface CoupleRepository {
    suspend fun generateCoupleInvitationCode() : String
    suspend fun connectCouple(invitationCode: String)
}