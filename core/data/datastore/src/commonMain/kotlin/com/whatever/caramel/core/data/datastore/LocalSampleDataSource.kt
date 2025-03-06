package com.whatever.caramel.core.data.datastore

interface LocalSampleDataSource {
    suspend fun getSampleName() : String
    suspend fun setSampleName(name : String)
}