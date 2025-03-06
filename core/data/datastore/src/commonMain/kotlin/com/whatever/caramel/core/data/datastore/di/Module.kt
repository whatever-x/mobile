package com.whatever.caramel.core.data.datastore.di

import com.whatever.caramel.core.data.datastore.SampleDatastore
import com.whatever.caramel.core.data.datastore.SampleDatastoreImpl
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformDataStoreModule: Module

val localDataSourceModule = module {
    single<SampleDatastore> { SampleDatastoreImpl(prefs = get()) }
}