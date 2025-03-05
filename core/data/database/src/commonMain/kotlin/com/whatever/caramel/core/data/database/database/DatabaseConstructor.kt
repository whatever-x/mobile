package com.whatever.caramel.core.data.database.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACUTAL_FOR_EXPECT")
expect object DatabaseConstructor : RoomDatabaseConstructor<SampleDatabase> {
    override fun initialize(): SampleDatabase
}