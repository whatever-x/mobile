package com.whatever.caramel.core.database.database.sample

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<SampleDatabase>
}
