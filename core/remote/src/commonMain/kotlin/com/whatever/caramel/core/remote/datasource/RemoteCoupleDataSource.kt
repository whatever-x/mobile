package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.couple.request.CoupleConnectRequest
import com.whatever.caramel.core.remote.dto.couple.request.CoupleSharedMessageRequest
import com.whatever.caramel.core.remote.dto.couple.request.CoupleStartDateUpdateRequest
import com.whatever.caramel.core.remote.dto.couple.response.CoupleAnniversaryResponse
import com.whatever.caramel.core.remote.dto.couple.response.CoupleBasicResponse
import com.whatever.caramel.core.remote.dto.couple.response.CoupleDetailResponse
import com.whatever.caramel.core.remote.dto.couple.response.CoupleInvitationCodeResponse

interface RemoteCoupleDataSource {
    suspend fun generateCoupleInvitationCode(): CoupleInvitationCodeResponse

    suspend fun sendInvitationCode(request: CoupleConnectRequest): CoupleDetailResponse

    suspend fun fetchCoupleRelationshipInfo(coupleId: Long): CoupleDetailResponse

    suspend fun updateShareMessage(
        coupleId: Long,
        request: CoupleSharedMessageRequest,
    ): CoupleBasicResponse

    suspend fun updateCoupleStartDate(
        coupleId: Long,
        request: CoupleStartDateUpdateRequest,
    ): CoupleBasicResponse

    suspend fun fetchMyCoupleInfo(): CoupleBasicResponse

    suspend fun fetchAnniversaryList(
        coupleId: Long,
        startDate: String,
        endDate: String,
    ): CoupleAnniversaryResponse

    // @ham2174 TODO : DELETE /v1/couples/{couple-id}/members/me 커플 나가기 API 연결
}
