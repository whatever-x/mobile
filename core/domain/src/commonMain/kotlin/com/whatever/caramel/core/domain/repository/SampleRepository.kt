package com.whatever.caramel.core.domain.repository

import com.whatever.caramel.core.domain.model.SampleModel

interface SampleRepository {

    suspend fun getSampleData(): SampleModel

    suspend fun saveSampleDataToLocal(data : SampleModel)

    suspend fun getSampleDataFromLocal() : List<SampleModel>

    suspend fun getSampleNameFromLocal() : String

    suspend fun saveSampleNameToLocal(name : String)
}
