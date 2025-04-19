package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toCouple
import com.whatever.caramel.core.data.mapper.toCoupleInvitationCode
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.datastore.datasource.CoupleDataSource
import com.whatever.caramel.core.domain.entity.Couple
import com.whatever.caramel.core.domain.vo.couple.CoupleInvitationCode
import com.whatever.caramel.core.domain.repository.CoupleRepository
import com.whatever.caramel.core.remote.datasource.RemoteCoupleDataSource
import com.whatever.caramel.core.remote.dto.couple.CoupleConnectRequest

class CoupleRepositoryImpl(
    private val localCoupleDataSource: CoupleDataSource,
    private val remoteCoupleDataSource: RemoteCoupleDataSource
) : CoupleRepository {
    override suspend fun getCoupleInvitationCode(): CoupleInvitationCode {
        return safeCall {
            remoteCoupleDataSource.generateCoupleInvitationCode().toCoupleInvitationCode()
        }
    }

    override suspend fun connectCouple(invitationCode: String): Couple {
        return safeCall {
            val request = CoupleConnectRequest(
                invitationCode = invitationCode
            )
            remoteCoupleDataSource.connectCouple(request).toCouple()
        }
    }

    override suspend fun setCoupleId(coupleId: Long) {
        safeCall {
            localCoupleDataSource.saveCoupleId(coupleId)
        }
    }

    override suspend fun getCoupleId(): Long {
        return safeCall {
            localCoupleDataSource.fetchCoupleId()
        }
    }

    override suspend fun getCoupleInfo(
        coupleId: Long
    ): Couple {
        return safeCall {
            remoteCoupleDataSource.getCoupleInfo(coupleId = coupleId).toCouple()
        }
    }
}