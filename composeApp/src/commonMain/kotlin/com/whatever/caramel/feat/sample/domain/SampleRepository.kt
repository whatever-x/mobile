package com.whatever.caramel.feat.sample.domain

interface SampleRepository {

    suspend fun getSampleData(): SampleModel

}
