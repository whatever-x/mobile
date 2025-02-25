package com.whatever.caramel.feat.sample.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SampleEntity::class, SampleDetailEntity::class],
    version = 1
)
@ConstructedBy(SampleDatabaseConstructor::class)
abstract class SampleDatabase : RoomDatabase() {
    abstract val sampleDao : SampleDao

    companion object {
        const val DB_NAME = "sample.db"
    }
}