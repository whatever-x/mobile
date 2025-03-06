package com.whatever.caramel.core.data.database.database.sample

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create() : RoomDatabase.Builder<SampleDatabase>
}