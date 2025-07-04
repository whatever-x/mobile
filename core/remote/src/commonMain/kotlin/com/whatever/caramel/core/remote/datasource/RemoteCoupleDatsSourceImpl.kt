package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.couple.request.CoupleConnectRequest
import com.whatever.caramel.core.remote.dto.couple.request.CoupleSharedMessageRequest
import com.whatever.caramel.core.remote.dto.couple.request.CoupleStartDateUpdateRequest
import com.whatever.caramel.core.remote.dto.couple.response.CoupleAnniversaryResponse
import com.whatever.caramel.core.remote.dto.couple.response.CoupleBasicResponse
import com.whatever.caramel.core.remote.dto.couple.response.CoupleDetailResponse
import com.whatever.caramel.core.remote.dto.couple.response.CoupleInvitationCodeResponse
import com.whatever.caramel.core.remote.network.config.Header
import com.whatever.caramel.core.remote.network.util.getBody
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.koin.core.annotation.Named

class RemoteCoupleDatsSourceImpl(
    @Named("AuthClient") private val authClient: HttpClient,
) : RemoteCoupleDataSource {
    override suspend fun generateCoupleInvitationCode(): CoupleInvitationCodeResponse =
        authClient.post(COUPLE_BASE_URL + "invitation-code").getBody()

    override suspend fun connectCouple(request: CoupleConnectRequest): CoupleDetailResponse =
        authClient
            .post(COUPLE_BASE_URL + "connect") {
                setBody(request)
            }.getBody()

    override suspend fun fetchCoupleRelationshipInfo(coupleId: Long): CoupleDetailResponse =
        authClient.get(COUPLE_BASE_URL + "$coupleId").getBody()

    override suspend fun patchShareMessage(
        coupleId: Long,
        request: CoupleSharedMessageRequest,
    ): CoupleBasicResponse =
        authClient
            .patch(COUPLE_BASE_URL + "$coupleId/shared-message") {
                setBody(body = request)
            }.getBody()

    override suspend fun updateCoupleStartDate(
        coupleId: Long,
        timeZone: String,
        request: CoupleStartDateUpdateRequest,
    ): CoupleBasicResponse =
        authClient
            .patch(COUPLE_BASE_URL + "$coupleId/start-date") {
                header(Header.TIME_ZONE, timeZone)
                setBody(request)
            }.getBody()

    override suspend fun getAnniversaries(
        coupleId: Long,
        startDate: String,
        endDate: String,
    ): CoupleAnniversaryResponse =
        authClient
            .get(COUPLE_BASE_URL + "$coupleId/anniversaries") {
                parameter("couple-id", coupleId)
                parameter("startDate", startDate)
                parameter("endDate", endDate)
            }.getBody()

    override suspend fun getCoupleInfo(): CoupleBasicResponse = authClient.get(COUPLE_BASE_URL + "me").getBody()

    companion object {
        private const val COUPLE_BASE_URL = "v1/couples/"
    }
}
