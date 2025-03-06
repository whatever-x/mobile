package com.whatever.caramel.core.datastore

interface SampleDatastore {
    suspend fun getSampleName() : String
    suspend fun setSampleName(name : String)
}