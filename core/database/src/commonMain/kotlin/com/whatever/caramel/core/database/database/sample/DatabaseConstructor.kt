package com.whatever.caramel.core.database.database.sample

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACUTAL_FOR_EXPECT")
expect object DatabaseConstructor : RoomDatabaseConstructor<SampleDatabase> {
    override fun initialize(): SampleDatabase
}