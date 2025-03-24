package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.user.UserProfileRequest
import com.whatever.caramel.core.remote.dto.user.UserProfileResponse
import com.whatever.caramel.core.remote.network.util.getBody
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.koin.core.annotation.Named

class RemoteUserDataSourceImpl(
    @Named("AuthClient") private val authClient: HttpClient,
) : RemoteUserDataSource {
    override suspend fun createUserProfile(request: UserProfileRequest): UserProfileResponse {
        return authClient.post(POST_CREATE_PROFILE) {
            setBody(request)
        }.getBody()
    }

    companion object {
        private const val POST_CREATE_PROFILE = "/v1/user/profile"
    }
}