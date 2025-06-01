package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.user.request.EditUserProfileRequest
import com.whatever.caramel.core.remote.dto.user.response.EditUserProfileResponse
import com.whatever.caramel.core.remote.dto.user.request.UserProfileRequest
import com.whatever.caramel.core.remote.dto.user.response.UserInfoResponse
import com.whatever.caramel.core.remote.dto.user.response.UserProfileResponse
import com.whatever.caramel.core.remote.network.util.getBody
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import org.koin.core.annotation.Named

class RemoteUserDataSourceImpl(
    @Named("AuthClient") private val authClient: HttpClient,
) : RemoteUserDataSource {

    override suspend fun createUserProfile(request: UserProfileRequest): UserProfileResponse {
        return authClient.post(USER_BASE_URL + "profile") {
            setBody(request)
        }.getBody()
    }

    override suspend fun editUserProfile(request: EditUserProfileRequest): EditUserProfileResponse {
        return authClient.put(USER_BASE_URL + "profile") {
            setBody(request)
        }.getBody()
    }

    override suspend fun getUserInfo(): UserInfoResponse {
        return authClient.get(USER_BASE_URL + "me").getBody()
    }

    override suspend fun signOut() {
        authClient.delete(USER_BASE_URL + "account")
    }

    companion object {
        private const val USER_BASE_URL = "/v1/user/"
    }

}