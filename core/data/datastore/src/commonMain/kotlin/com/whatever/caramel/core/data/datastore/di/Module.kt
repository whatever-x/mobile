package com.whatever.caramel.core.data.datastore.di

import com.whatever.caramel.core.data.datastore.LocalSampleDatastore
import com.whatever.caramel.core.data.datastore.LocalSampleDatastoreImpl
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformDataStoreModule: Module

val localDataSourceModule = module {
    single<LocalSampleDatastore> { LocalSampleDatastoreImpl(prefs = get()) }
}