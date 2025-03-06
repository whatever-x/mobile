package com.whatever.caramel.core.datastore.di

import com.whatever.caramel.core.datastore.SampleDatastore
import com.whatever.caramel.core.datastore.SampleDatastoreImpl
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformDataStoreModule: Module

val localDataSourceModule = module {
    single<SampleDatastore> { SampleDatastoreImpl(prefs = get()) }
}