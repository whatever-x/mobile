package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.auth.ServiceTokenDto
import com.whatever.caramel.core.remote.dto.auth.request.SignInRequest
import com.whatever.caramel.core.remote.dto.auth.response.SignInResponse
import com.whatever.caramel.core.remote.dto.user.response.UserSessionRefreshResponse

interface RemoteAuthDataSource {
    suspend fun signIn(request: SignInRequest): SignInResponse

    suspend fun logOut()

    suspend fun refresh(request: ServiceTokenDto): ServiceTokenDto

    suspend fun deleteAccount()

    suspend fun signInV2(request: SignInRequest): SignInResponse

    suspend fun refreshV2(request: ServiceTokenDto): UserSessionRefreshResponse
}
