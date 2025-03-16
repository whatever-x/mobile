package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.auth.ServiceToken
import com.whatever.caramel.core.remote.dto.auth.SignInRequest
import com.whatever.caramel.core.remote.dto.auth.SignInResponse
import com.whatever.caramel.core.remote.dto.sample.request.SamplePostMethodRequestDto
import com.whatever.caramel.core.remote.dto.sample.response.SampleGetMethodResponseDto
import com.whatever.caramel.core.remote.dto.sample.response.SamplePostMethodResponseDto
import com.whatever.caramel.core.remote.network.util.getBody
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.appendPathSegments
import org.koin.core.annotation.Named

internal class RemoteAuthDataSourceImpl(
    @Named("AuthClient") private val authClient: HttpClient,
    @Named("DefaultClient") private val defaultClient: HttpClient,
    @Named("SampleClient") private val sampleClient: HttpClient,
) : RemoteAuthDataSource {

    override suspend fun testSignIn(): SignInResponse =
        defaultClient.get(TEST_SIGN_IN_URL) {
            parameter("gender", "MALE")
            parameter("expSec", 10)
        }.getBody()

    override suspend fun signIn(request: SignInRequest): SignInResponse =
        defaultClient.post(POST_SIGN_IN_URL) {
            setBody(body = request)
        }.getBody()

    // @ham2174 TODO : 리프레쉬 API 서버 로직 구현시 수정
    override suspend fun refresh(refreshToken: String): ServiceToken =
        defaultClient.post(POST_REFRESH_URL) {
            bearerAuth(token = refreshToken)
        }.getBody()

    // @ham2174 TODO : 샘플 로직 제거
    /* Sample */
    override suspend fun getSampleData(): SampleGetMethodResponseDto =
        sampleClient.get(GET_SAMPLE_URL).getBody()

    override suspend fun postSampleData(
        request: SamplePostMethodRequestDto
    ): SamplePostMethodResponseDto =
        sampleClient.post(POST_SAMPLE_URL) {
            setBody(request)
        }.getBody()

    override suspend fun getSampleExceptions(
        exceptionNumber: String
    ): SampleGetMethodResponseDto =
        sampleClient.get(GET_SAMPLE_EXCEPTION_URL) {
            url {
                appendPathSegments(exceptionNumber)
            }
        }.getBody()

    companion object {
        private const val TEST_SIGN_IN_URL = "/test/sign-in"
        private const val POST_SIGN_IN_URL = "/v1/auth/sign-in"
        private const val POST_REFRESH_URL = "/v1/auth/refresh"

        private const val GET_SAMPLE_URL = "sample"
        private const val POST_SAMPLE_URL = "sample"
        private const val GET_SAMPLE_EXCEPTION_URL = "sample/exception"
    }

}