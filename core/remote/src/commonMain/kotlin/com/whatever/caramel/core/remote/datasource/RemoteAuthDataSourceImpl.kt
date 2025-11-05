package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.auth.ServiceTokenDto
import com.whatever.caramel.core.remote.dto.auth.request.SignInRequest
import com.whatever.caramel.core.remote.dto.auth.response.SignInResponse
import com.whatever.caramel.core.remote.dto.user.response.UserSessionRefreshResponse
import com.whatever.caramel.core.remote.network.util.getBody
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.koin.core.annotation.Named

internal class RemoteAuthDataSourceImpl(
    @Named("DefaultClient") private val defaultClient: HttpClient,
    @Named("AuthClient") private val authClient: HttpClient,
) : RemoteAuthDataSource {

    @Deprecated("userId 필드 추가 됨에 따라 `signInV2` API로 대체 되었습니다.")
    override suspend fun signIn(request: SignInRequest): SignInResponse =
        defaultClient
            .post("$BASE_AUTH_URL/sign-in") {
                setBody(body = request)
            }.getBody()

    override suspend fun logOut() {
        authClient.post("$BASE_AUTH_URL/sign-out")
    }

    override suspend fun refresh(request: ServiceTokenDto): ServiceTokenDto =
        defaultClient
            .post("$BASE_AUTH_URL/refresh") {
                setBody(body = request)
            }.getBody()

    override suspend fun deleteAccount() {
        authClient.delete("$BASE_AUTH_URL/account")
    }

    override suspend fun signInV2(request: SignInRequest): SignInResponse =
        defaultClient
            .post("$BASE_AUTH_V2_URL/sign-in") {
                setBody(body = request)
            }.getBody()

    override suspend fun refreshV2(request: ServiceTokenDto): UserSessionRefreshResponse =
        authClient
            .post("$BASE_AUTH_V2_URL/refresh") {
                setBody(body = request)
            }.getBody()

    companion object {
        private const val BASE_AUTH_URL = "/v1/auth"
        private const val BASE_AUTH_V2_URL = "/v2/auth"
    }
}
