package com.whatever.caramel.core.data.datastore.di

import com.whatever.caramel.core.data.datastore.LocalSampleDataSource
import com.whatever.caramel.core.data.datastore.LocalSampleDataSourceImpl
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformDataStoreModule: Module

val localDataSourceModule = module {
    single<LocalSampleDataSource> { LocalSampleDataSourceImpl(prefs = get()) }
}