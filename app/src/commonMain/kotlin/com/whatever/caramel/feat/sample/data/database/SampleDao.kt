package com.whatever.caramel.feat.sample.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert

@Dao
interface SampleDao {
    @Upsert
    suspend fun insertSampleEntity(sampleEntity: SampleEntity)

    @Upsert
    suspend fun insertSampleDetailEntities(sampleDetailEntity: List<SampleDetailEntity>)

    @Transaction
    @Upsert
    suspend fun insertSampleWithDetail(sampleEntity: SampleEntity, sampleDetailEntity: List<SampleDetailEntity>)

    @Query("SELECT * FROM sample")
    suspend fun getAllSamples() : List<SampleEntityWithDetail>
}