package com.whatever.caramel.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

// @RyuSw-cs 2025.02.25 SampleGetMethodResponseDto 기준으로 작성
@Entity(tableName = "sample")
data class SampleEntity(
    @PrimaryKey
    @ColumnInfo("name")

    val name: String,
    @ColumnInfo("local_data_time")
    val localDateTime: String
)

@Entity(tableName = "sample_detail")
data class SampleDetailEntity(
    @PrimaryKey
    @ColumnInfo("description")
    val description: String,
    @ColumnInfo("sample_name")
    val sampleName: String
)

data class SampleEntityWithDetail(
    @Embedded val sample : SampleEntity,
    @Relation(
        parentColumn = "name",
        entityColumn = "sample_name"
    )
    val detailArray : List<SampleDetailEntity>
)