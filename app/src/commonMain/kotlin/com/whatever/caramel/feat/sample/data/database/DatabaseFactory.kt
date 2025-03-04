package com.whatever.caramel.feat.sample.data.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create() : RoomDatabase.Builder<SampleDatabase>
}