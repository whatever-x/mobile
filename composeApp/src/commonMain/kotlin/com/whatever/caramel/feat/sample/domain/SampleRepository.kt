package com.whatever.caramel.feat.sample.domain

interface SampleRepository {

    suspend fun getSampleData(): SampleModel

    suspend fun saveSampleDataToLocal(data : SampleModel)

    suspend fun getSampleDataFromLocal() : List<SampleModel>

}
