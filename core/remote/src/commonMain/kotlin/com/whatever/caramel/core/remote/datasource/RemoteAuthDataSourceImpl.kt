package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.auth.ServiceToken
import com.whatever.caramel.core.remote.dto.auth.SignInRequest
import com.whatever.caramel.core.remote.dto.auth.SignInResponse
import com.whatever.caramel.core.remote.network.util.getBody
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.koin.core.annotation.Named

internal class RemoteAuthDataSourceImpl(
    @Named("DefaultClient") private val defaultClient: HttpClient,
) : RemoteAuthDataSource {
    override suspend fun signIn(request: SignInRequest): SignInResponse =
        defaultClient.post(POST_SIGN_IN_URL) {
            setBody(body = request)
        }.getBody()

    override suspend fun refresh(request: ServiceToken): ServiceToken =
        defaultClient.post(POST_REFRESH_URL) {
            setBody(body = request)
        }.getBody()

    companion object {
        private const val POST_SIGN_IN_URL = "/v1/auth/sign-in"
        private const val POST_REFRESH_URL = "/v1/auth/refresh"
    }

}