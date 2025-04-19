package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.couple.CoupleConnectRequest
import com.whatever.caramel.core.remote.dto.couple.CoupleConnectResponse
import com.whatever.caramel.core.remote.dto.couple.CoupleInfoResponse
import com.whatever.caramel.core.remote.dto.couple.CoupleInvitationCodeResponse

interface RemoteCoupleDataSource {
    suspend fun generateCoupleInvitationCode(): CoupleInvitationCodeResponse
    suspend fun connectCouple(request: CoupleConnectRequest): CoupleConnectResponse
    suspend fun getCoupleInfo(coupleId : Long) : CoupleInfoResponse
}