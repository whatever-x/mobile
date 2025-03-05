package com.whatever.caramel.core.data.datastore.di

import com.whatever.caramel.core.data.datastore.SampleLocalDataSource
import com.whatever.caramel.core.data.datastore.SampleLocalDataSourceImpl
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformDataStoreModule: Module

val localDataSourceModule = module {
    single<SampleLocalDataSource> { SampleLocalDataSourceImpl(prefs = get()) }
}