package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.auth.ServiceToken
import com.whatever.caramel.core.remote.dto.auth.SignInRequest
import com.whatever.caramel.core.remote.dto.auth.SignInResponse

interface RemoteAuthDataSource {
    suspend fun signIn(
        request: SignInRequest
    ): SignInResponse

    suspend fun refresh(
        request: ServiceToken
    ): ServiceToken
}