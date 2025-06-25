package com.whatever.caramel.core.database.database.sample

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.whatever.caramel.core.database.dao.SampleDao
import com.whatever.caramel.core.database.entity.SampleDetailEntity
import com.whatever.caramel.core.database.entity.SampleEntity

@Database(
    entities = [SampleEntity::class, SampleDetailEntity::class],
    version = 1
)
@ConstructedBy(DatabaseConstructor::class)
abstract class SampleDatabase : RoomDatabase() {
    abstract val sampleDao : SampleDao

    companion object {
        const val DB_NAME = "sample.db"
    }
}