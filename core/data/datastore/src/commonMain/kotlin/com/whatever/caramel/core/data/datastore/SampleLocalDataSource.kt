package com.whatever.caramel.core.data.datastore

interface SampleLocalDataSource {
    suspend fun getSampleName() : String
    suspend fun setSampleName(name : String)
}