package com.whatever.caramel.core.data.datastore

interface LocalSampleDatastore {
    suspend fun getSampleName() : String
    suspend fun setSampleName(name : String)
}