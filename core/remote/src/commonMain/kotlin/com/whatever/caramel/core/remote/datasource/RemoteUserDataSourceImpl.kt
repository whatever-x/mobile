package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.user.request.EditUserProfileRequest
import com.whatever.caramel.core.remote.dto.user.response.EditUserProfileResponse
import com.whatever.caramel.core.remote.dto.user.request.UserProfileRequest
import com.whatever.caramel.core.remote.dto.user.response.UserProfileResponse
import com.whatever.caramel.core.remote.network.util.getBody
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import org.koin.core.annotation.Named

class RemoteUserDataSourceImpl(
    @Named("AuthClient") private val authClient: HttpClient,
) : RemoteUserDataSource {
    override suspend fun createUserProfile(request: UserProfileRequest): UserProfileResponse {
        return authClient.post(PROFILE_URL) {
            setBody(request)
        }.getBody()
    }

    override suspend fun editUserProfile(request: EditUserProfileRequest): EditUserProfileResponse {
        return authClient.put(PROFILE_URL) {
            setBody(request)
        }.getBody()
    }

    companion object {
        private const val PROFILE_URL = "/v1/user/profile"
    }
}