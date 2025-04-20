package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.couple.CoupleConnectRequest
import com.whatever.caramel.core.remote.dto.couple.CoupleConnectResponse
import com.whatever.caramel.core.remote.dto.couple.CoupleInfoResponse
import com.whatever.caramel.core.remote.dto.couple.CoupleInvitationCodeResponse
import com.whatever.caramel.core.remote.dto.couple.CoupleStartDateUpdateRequest
import com.whatever.caramel.core.remote.dto.couple.CoupleStartDateUpdateResponse
import com.whatever.caramel.core.remote.network.util.getBody
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.koin.core.annotation.Named

class RemoteCoupleDatsSourceImpl(
    @Named("AuthClient") private val authClient: HttpClient
) : RemoteCoupleDataSource {
    override suspend fun generateCoupleInvitationCode(): CoupleInvitationCodeResponse {
        return authClient.post(POST_COUPLE_INVITATION_CODE_URL).getBody()
    }

    override suspend fun connectCouple(request: CoupleConnectRequest): CoupleConnectResponse {
        return authClient.post(POST_CONNECT_COUPLE_URL) {
            setBody(request)
        }.getBody()
    }

    override suspend fun getCoupleInfo(coupleId: Long): CoupleInfoResponse {
        return authClient.get("$COUPLE_BASE_URL/$coupleId").getBody()
    }

    override suspend fun updateCoupleStartDate(
        coupleId : Long,
        request : CoupleStartDateUpdateRequest
    ): CoupleStartDateUpdateResponse {
        return authClient.patch("$COUPLE_BASE_URL/$coupleId$PATCH_COUPLE_START_DATE_POSTFIX"){
            setBody(request)
        }.getBody()
    }

    companion object {
        private const val COUPLE_BASE_URL = "v1/couples"
        private const val POST_COUPLE_INVITATION_CODE_URL = "$COUPLE_BASE_URL/invitation-code"
        private const val POST_CONNECT_COUPLE_URL = "$COUPLE_BASE_URL/connect"
        private const val PATCH_COUPLE_START_DATE_POSTFIX = "/start-date"
    }
}