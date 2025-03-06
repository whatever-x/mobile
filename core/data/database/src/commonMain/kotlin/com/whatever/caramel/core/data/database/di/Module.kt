package com.whatever.caramel.core.data.database.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.whatever.caramel.core.data.database.database.DatabaseFactory
import com.whatever.caramel.core.data.database.database.SampleDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformDatabaseModule : Module

val databaseModule = module {
    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    single {
        get<SampleDatabase>().sampleDao
    }
}