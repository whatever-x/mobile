package com.whatever.caramel.core.data.remote.datasource

import com.whatever.caramel.core.data.remote.dto.request.SamplePostMethodRequestDto
import com.whatever.caramel.core.data.remote.dto.response.SampleGetMethodResponseDto
import com.whatever.caramel.core.data.remote.dto.response.SamplePostMethodResponseDto

interface RemoteSampleDataSource {

    suspend fun getSampleData(): SampleGetMethodResponseDto

    suspend fun postSampleData(
        request: SamplePostMethodRequestDto
    ): SamplePostMethodResponseDto

    suspend fun getSampleExceptions(
        exceptionNumber: String
    ): SampleGetMethodResponseDto

}