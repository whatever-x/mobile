package com.whatever.caramel.feat.sample.data.remote

import com.whatever.caramel.feat.sample.data.dto.request.SamplePostMethodRequestDto
import com.whatever.caramel.feat.sample.data.dto.response.SampleGetMethodResponseDto
import com.whatever.caramel.feat.sample.data.dto.response.SamplePostMethodResponseDto

interface RemoteSampleDataSource {

    suspend fun getSampleData(): SampleGetMethodResponseDto

    suspend fun postSampleData(
        request: SamplePostMethodRequestDto
    ): SamplePostMethodResponseDto

    suspend fun getSampleExceptions(
        exceptionNumber: String
    ): SampleGetMethodResponseDto

}