package com.whatever.caramel.core.data.datastore

interface SampleLocalDataStore {
    suspend fun getSampleName() : String
    suspend fun setSampleName(name : String)
}