package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.auth.ServiceToken
import com.whatever.caramel.core.remote.dto.auth.SignInRequest
import com.whatever.caramel.core.remote.dto.auth.SignInResponse
import com.whatever.caramel.core.remote.dto.sample.request.SamplePostMethodRequestDto
import com.whatever.caramel.core.remote.dto.sample.response.SampleGetMethodResponseDto
import com.whatever.caramel.core.remote.dto.sample.response.SamplePostMethodResponseDto

interface RemoteAuthDataSource {

    suspend fun testSignIn(): SignInResponse

    suspend fun signIn(
        request: SignInRequest
    ): SignInResponse

    suspend fun refresh(
        request: ServiceToken
    ): ServiceToken

    // @ham2174 TODO : 샘플 로직 제거
    /* Sample */

    suspend fun getSampleData(): SampleGetMethodResponseDto

    suspend fun postSampleData(
        request: SamplePostMethodRequestDto
    ): SamplePostMethodResponseDto

    suspend fun getSampleExceptions(
        exceptionNumber: String
    ): SampleGetMethodResponseDto

}