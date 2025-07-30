package com.whatever.caramel.core.data.repository

import com.whatever.caramel.core.data.mapper.toAnniversaries
import com.whatever.caramel.core.data.mapper.toCouple
import com.whatever.caramel.core.data.mapper.toCoupleInvitationCode
import com.whatever.caramel.core.data.mapper.toCoupleRelationship
import com.whatever.caramel.core.data.util.safeCall
import com.whatever.caramel.core.datastore.datasource.CoupleDataSource
import com.whatever.caramel.core.domain.entity.Couple
import com.whatever.caramel.core.domain.repository.CoupleRepository
import com.whatever.caramel.core.domain.vo.couple.Anniversary
import com.whatever.caramel.core.domain.vo.couple.CoupleInvitationCode
import com.whatever.caramel.core.domain.vo.couple.CoupleRelationship
import com.whatever.caramel.core.remote.datasource.RemoteCoupleDataSource
import com.whatever.caramel.core.remote.dto.couple.request.CoupleConnectRequest
import com.whatever.caramel.core.remote.dto.couple.request.CoupleSharedMessageRequest
import com.whatever.caramel.core.remote.dto.couple.request.CoupleStartDateUpdateRequest

class CoupleRepositoryImpl(
    private val localCoupleDataSource: CoupleDataSource,
    private val remoteCoupleDataSource: RemoteCoupleDataSource,
) : CoupleRepository {
    override suspend fun getCoupleInvitationCode(): CoupleInvitationCode =
        safeCall {
            remoteCoupleDataSource.generateCoupleInvitationCode().toCoupleInvitationCode()
        }

    override suspend fun connectCouple(invitationCode: String): CoupleRelationship =
        safeCall {
            val request =
                CoupleConnectRequest(
                    invitationCode = invitationCode,
                )
            remoteCoupleDataSource.connectCouple(request).toCoupleRelationship()
        }

    override suspend fun setCoupleId(coupleId: Long) {
        safeCall {
            localCoupleDataSource.saveCoupleId(coupleId)
        }
    }

    override suspend fun getCoupleId(): Long =
        safeCall {
            localCoupleDataSource.fetchCoupleId()
        }

    override suspend fun getCoupleRelationshipInfo(coupleId: Long): CoupleRelationship =
        safeCall {
            remoteCoupleDataSource.fetchCoupleRelationshipInfo(coupleId = coupleId).toCoupleRelationship()
        }

    override suspend fun editCoupleStartDate(
        coupleId: Long,
        startDate: String,
    ): Couple =
        safeCall {
            val request =
                CoupleStartDateUpdateRequest(
                    startDate = startDate,
                )
            remoteCoupleDataSource
                .updateCoupleStartDate(
                    coupleId = coupleId,
                    request = request,
                ).toCouple()
        }

    override suspend fun updateShareMessage(
        coupleId: Long,
        shareMessage: String,
    ): Couple =
        safeCall {
            val request = CoupleSharedMessageRequest(sharedMessage = shareMessage)

            remoteCoupleDataSource
                .patchShareMessage(
                    coupleId = coupleId,
                    request = request,
                ).toCouple()
        }

    override suspend fun getAnniversaries(
        coupleId: Long,
        startDate: String,
        endDate: String,
    ): List<Anniversary> =
        safeCall {
            remoteCoupleDataSource
                .getAnniversaries(
                    coupleId = coupleId,
                    startDate = startDate,
                    endDate = endDate,
                ).toAnniversaries()
        }

    override suspend fun deleteCoupleId() {
        safeCall { localCoupleDataSource.deleteCoupleId() }
    }

    override suspend fun getCoupleInfo(): Couple =
        safeCall {
            remoteCoupleDataSource.getCoupleInfo().toCouple()
        }
}
