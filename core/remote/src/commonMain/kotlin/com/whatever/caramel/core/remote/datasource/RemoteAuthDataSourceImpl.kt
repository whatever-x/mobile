package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.auth.ServiceTokenDto
import com.whatever.caramel.core.remote.dto.auth.request.SignInRequest
import com.whatever.caramel.core.remote.dto.auth.response.SignInResponse
import com.whatever.caramel.core.remote.network.util.getBody
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.koin.core.annotation.Named

internal class RemoteAuthDataSourceImpl(
    @Named("DefaultClient") private val defaultClient: HttpClient,
    @Named("AuthClient") private val authClient: HttpClient
) : RemoteAuthDataSource {
    override suspend fun signIn(request: SignInRequest): SignInResponse =
        defaultClient.post(BASE_AUTH_URL + "sign-in") {
            setBody(body = request)
        }.getBody()

    override suspend fun refresh(request: ServiceTokenDto): ServiceTokenDto =
        defaultClient.post(BASE_AUTH_URL + "refresh") {
            setBody(body = request)
        }.getBody()

    override suspend fun signOut() {
        authClient.delete(BASE_AUTH_URL + "account")

    }

    companion object {
        private const val BASE_AUTH_URL = "/v1/auth/"
    }

}