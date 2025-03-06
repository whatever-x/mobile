package com.whatever.caramel.core.data.datastore.di

import com.whatever.caramel.core.data.datastore.SampleLocalDataStore
import com.whatever.caramel.core.data.datastore.SampleLocalDataStoreImpl
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformDataStoreModule: Module

val localDataSourceModule = module {
    single<SampleLocalDataStore> { SampleLocalDataStoreImpl(prefs = get()) }
}