package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.user.request.EditUserProfileRequest
import com.whatever.caramel.core.remote.dto.user.response.EditUserProfileResponse
import com.whatever.caramel.core.remote.dto.user.request.UserProfileRequest
import com.whatever.caramel.core.remote.dto.user.response.UserInfoResponse
import com.whatever.caramel.core.remote.dto.user.response.UserProfileResponse

interface RemoteUserDataSource {
    suspend fun createUserProfile(request: UserProfileRequest): UserProfileResponse
    suspend fun editUserProfile(request: EditUserProfileRequest): EditUserProfileResponse
    suspend fun getUserInfo(): UserInfoResponse
    suspend fun signOut()
}