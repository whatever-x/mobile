package com.whatever.caramel.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.whatever.caramel.core.database.entity.SampleDetailEntity
import com.whatever.caramel.core.database.entity.SampleEntity
import com.whatever.caramel.core.database.entity.SampleEntityWithDetail

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