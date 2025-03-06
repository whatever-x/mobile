package com.whatever.caramel.core.database.di

import com.whatever.caramel.core.database.database.sample.DatabaseFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformDatabaseModule: Module
    get() = module {
        single { DatabaseFactory() }
    }