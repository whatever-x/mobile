package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.user.EditUserProfileRequest
import com.whatever.caramel.core.remote.dto.user.EditUserProfileResponse
import com.whatever.caramel.core.remote.dto.user.UserProfileRequest
import com.whatever.caramel.core.remote.dto.user.UserProfileResponse

interface RemoteUserDataSource {
    suspend fun createUserProfile(request: UserProfileRequest): UserProfileResponse
    suspend fun editUserProfile(request: EditUserProfileRequest): EditUserProfileResponse
}