package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.domain.repository.CoupleRepository
import com.whatever.caramel.core.remote.datasource.RemoteCoupleDataSource
import com.whatever.caramel.core.remote.dto.couple.CoupleConnectRequest

class CoupleRepositoryImpl(
    private val coupleRemoteDataSource: RemoteCoupleDataSource
) : CoupleRepository {
    override suspend fun generateCoupleInvitationCode(): String {
        return safeCall {
            coupleRemoteDataSource.generateCoupleInvitationCode().invitationCode
        }
    }

    override suspend fun connectCouple(invitationCode: String) {
        val request = CoupleConnectRequest(invitationCode = invitationCode)
        safeCall {
            coupleRemoteDataSource.connectCouple(request)
        }
    }
}