package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toCoupleInvitationCode
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.domain.vo.couple.CoupleInvitationCode
import com.whatever.caramel.core.domain.repository.CoupleRepository
import com.whatever.caramel.core.remote.datasource.RemoteCoupleDataSource
import com.whatever.caramel.core.remote.dto.couple.CoupleConnectRequest

class CoupleRepositoryImpl(
    private val remoteCoupleDataSource: RemoteCoupleDataSource
) : CoupleRepository {
    override suspend fun getCoupleInvitationCode(): CoupleInvitationCode {
        return safeCall {
            remoteCoupleDataSource.generateCoupleInvitaionCode().toCoupleInvitationCode()
        }
    }

    override suspend fun connectCouple(invitationCode: String) {
        safeCall {
            val request = CoupleConnectRequest(
                invitationCode = invitationCode
            )
            remoteCoupleDataSource.connectCouple(request)
        }
    }
}