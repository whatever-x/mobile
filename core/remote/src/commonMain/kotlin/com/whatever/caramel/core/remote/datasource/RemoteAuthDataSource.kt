package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.auth.ServiceTokenDto
import com.whatever.caramel.core.remote.dto.auth.request.SignInRequest
import com.whatever.caramel.core.remote.dto.auth.response.SignInResponse

interface RemoteAuthDataSource {
    suspend fun signIn(
        request: SignInRequest
    ): SignInResponse

    suspend fun refresh(
        request: ServiceTokenDto
    ): ServiceTokenDto
}