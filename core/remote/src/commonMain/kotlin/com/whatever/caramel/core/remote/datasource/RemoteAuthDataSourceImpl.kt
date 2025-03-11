package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.auth.SignInRequest
import com.whatever.caramel.core.remote.dto.auth.SignInResponse
import com.whatever.caramel.core.remote.network.getBody
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody

internal class RemoteAuthDataSourceImpl(
    private val client: HttpClient
) : RemoteAuthDataSource {

    override suspend fun signIn(request: SignInRequest): SignInResponse =
        client.post(POST_SIGN_IN_URL) {
            setBody(body = request)
        }.getBody()

    companion object {
        private const val POST_SIGN_IN_URL = "/v1/auth/sign-in"
    }

}