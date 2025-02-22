package com.whatever.caramel.feat.sample.data.remote

import com.whatever.caramel.core.data.getBody
import com.whatever.caramel.feat.sample.data.dto.request.SamplePostMethodRequestDto
import com.whatever.caramel.feat.sample.data.dto.response.SampleGetMethodResponseDto
import com.whatever.caramel.feat.sample.data.dto.response.SamplePostMethodResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.appendPathSegments

class RemoteSampleDataSourceImpl(
    private val client: HttpClient
) : RemoteSampleDataSource {

    override suspend fun getSampleData(): SampleGetMethodResponseDto =
        client.get(GET_SAMPLE_URL).getBody()

    override suspend fun postSampleData(
        request: SamplePostMethodRequestDto
    ): SamplePostMethodResponseDto =
        client.post(POST_SAMPLE_URL) {
            setBody(request)
        }.getBody()

    override suspend fun getSampleExceptions(
        exceptionNumber: String
    ): SampleGetMethodResponseDto =
        client.get(GET_SAMPLE_EXCEPTION_URL) {
            url {
                appendPathSegments(exceptionNumber)
            }
        }.getBody()

    companion object {
        private const val GET_SAMPLE_URL = "sample"
        private const val POST_SAMPLE_URL = "sample"
        private const val GET_SAMPLE_EXCEPTION_URL = "sample/exception"
    }
}