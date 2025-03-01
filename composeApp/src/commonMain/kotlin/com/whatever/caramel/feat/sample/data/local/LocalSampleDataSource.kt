package com.whatever.caramel.feat.sample.data.local

import com.whatever.caramel.feat.sample.data.database.SampleDetailEntity
import com.whatever.caramel.feat.sample.data.database.SampleEntity
import com.whatever.caramel.feat.sample.data.database.SampleEntityWithDetail

interface LocalSampleDataSource {
    suspend fun getSampleName() : String
    suspend fun setSampleName(name : String)
    suspend fun saveSampleData(sampleEntity : SampleEntity, sampleDetailEntities : List<SampleDetailEntity>)
    suspend fun getSampleData() : List<SampleEntityWithDetail>
}