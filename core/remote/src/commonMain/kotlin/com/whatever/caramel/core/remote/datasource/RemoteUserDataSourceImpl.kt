package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.user.request.EditUserProfileRequest
import com.whatever.caramel.core.remote.dto.user.request.UserProfileRequest
import com.whatever.caramel.core.remote.dto.user.request.UserSettingRequest
import com.whatever.caramel.core.remote.dto.user.response.EditUserProfileResponse
import com.whatever.caramel.core.remote.dto.user.response.UserInfoResponse
import com.whatever.caramel.core.remote.dto.user.response.UserProfileResponse
import com.whatever.caramel.core.remote.dto.user.response.UserSettingResponse
import com.whatever.caramel.core.remote.network.util.getBody
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.patch
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

    override suspend fun patchUserSetting(request: UserSettingRequest): UserSettingResponse {
        return authClient.patch(USER_BASE_URL + "settings") {
            setBody(request)
        }.getBody()
    }

    override suspend fun getUserSetting(): UserSettingResponse {
        return authClient.get(USER_BASE_URL + "settings").getBody()
    }

    companion object {
        private const val USER_BASE_URL = "/v1/user/"
    }
}
