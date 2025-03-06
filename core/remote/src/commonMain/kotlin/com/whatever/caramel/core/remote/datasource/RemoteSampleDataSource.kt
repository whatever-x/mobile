package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.request.SamplePostMethodRequestDto
import com.whatever.caramel.core.remote.dto.response.SampleGetMethodResponseDto
import com.whatever.caramel.core.remote.dto.response.SamplePostMethodResponseDto

interface RemoteSampleDataSource {

    suspend fun getSampleData(): SampleGetMethodResponseDto

    suspend fun postSampleData(
        request: SamplePostMethodRequestDto
    ): SamplePostMethodResponseDto

    suspend fun getSampleExceptions(
        exceptionNumber: String
    ): SampleGetMethodResponseDto

}