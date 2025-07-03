package com.whatever.caramel.core.database.database.sample

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(
    private val context: Context,
) {
    actual fun create(): RoomDatabase.Builder<SampleDatabase> {
        val applicationContext = context.applicationContext
        val db = applicationContext.getDatabasePath(SampleDatabase.DB_NAME)

        return Room.databaseBuilder(
            context = applicationContext,
            name = db.absolutePath,
        )
    }
}
