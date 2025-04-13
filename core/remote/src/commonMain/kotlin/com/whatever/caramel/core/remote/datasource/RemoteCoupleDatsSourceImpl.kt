package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.couple.CoupleConnectRequest
import com.whatever.caramel.core.remote.dto.couple.CoupleConnectResponse
import com.whatever.caramel.core.remote.dto.couple.CoupleInfoResponse
import com.whatever.caramel.core.remote.dto.couple.CoupleInvitationCodeResponse
import com.whatever.caramel.core.remote.network.util.getBody
import io.ktor.client.HttpClient
import io.ktor.client.request.get
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
        return authClient.get("$GET_COUPLE_INFO_URL/$coupleId").getBody()
    }

    companion object {
        private const val POST_COUPLE_INVITATION_CODE_URL = "v1/couples/invitation-code"
        private const val POST_CONNECT_COUPLE_URL = "v1/couples/connect"
        private const val GET_COUPLE_INFO_URL = "v1/couples"
    }
}