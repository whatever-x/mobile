package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.couple.CoupleConnectRequest
import com.whatever.caramel.core.remote.dto.couple.CoupleConnectResponse
import com.whatever.caramel.core.remote.dto.couple.CoupleInvitationCodeResponse
import com.whatever.caramel.core.remote.network.util.getBody
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.koin.core.annotation.Named

class RemoteCoupleDataSourceImpl(
    @Named("AuthClient") private val authClient: HttpClient
) : RemoteCoupleDataSource {
    override suspend fun generateCoupleInvitationCode(): CoupleInvitationCodeResponse {
        return authClient.post(POST_COUPLE_INVITATION_CODE_URL).getBody()
    }

    override suspend fun connectCouple(request: CoupleConnectRequest): CoupleConnectResponse {
        return authClient.post(POST_COUPLE_CONNECT_URL) {
            setBody(body = request)
        }.getBody()
    }

    companion object {
        private const val POST_COUPLE_INVITATION_CODE_URL = "v1/couples/invitation-code"
        private const val POST_COUPLE_CONNECT_URL = "v1/couples/connect"
    }
}