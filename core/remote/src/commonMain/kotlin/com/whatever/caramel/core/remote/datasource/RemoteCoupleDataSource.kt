package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.couple.request.CoupleConnectRequest
import com.whatever.caramel.core.remote.dto.couple.request.CoupleSharedMessageRequest
import com.whatever.caramel.core.remote.dto.couple.response.CoupleBasicResponse
import com.whatever.caramel.core.remote.dto.couple.response.CoupleDetailResponse
import com.whatever.caramel.core.remote.dto.couple.response.CoupleInvitationCodeResponse
import com.whatever.caramel.core.remote.dto.couple.request.CoupleStartDateUpdateRequest

interface RemoteCoupleDataSource {
    suspend fun generateCoupleInvitationCode(): CoupleInvitationCodeResponse
    suspend fun connectCouple(request: CoupleConnectRequest): CoupleDetailResponse
    suspend fun fetchCoupleRelationshipInfo(coupleId: Long): CoupleDetailResponse
    suspend fun patchShareMessage(
        coupleId: Long,
        request: CoupleSharedMessageRequest
    ): CoupleBasicResponse

    suspend fun updateCoupleStartDate(
        coupleId: Long,
        timeZone: String,
        request: CoupleStartDateUpdateRequest
    ): CoupleBasicResponse
}