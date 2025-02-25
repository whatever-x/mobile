package com.whatever.caramel.feat.sample.data.local

interface LocalSampleDataSource {
    suspend fun getSampleName() : String
    suspend fun setSampleName(name : String)
}