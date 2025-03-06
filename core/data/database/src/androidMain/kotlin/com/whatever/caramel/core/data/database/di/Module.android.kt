package com.whatever.caramel.core.data.database.di

import com.whatever.caramel.core.data.database.database.DatabaseFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformDatabaseModule: Module
    get() = module {
        single { DatabaseFactory(androidApplication()) }
    }