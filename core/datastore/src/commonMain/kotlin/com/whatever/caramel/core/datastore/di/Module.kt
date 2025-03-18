package com.whatever.caramel.core.datastore.di

import com.whatever.caramel.core.datastore.SampleDatastore
import com.whatever.caramel.core.datastore.SampleDatastoreImpl
import com.whatever.caramel.core.datastore.datasource.TokenDataSource
import com.whatever.caramel.core.datastore.datasource.TokenDataSourceImpl
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformDataStoreModule: Module

val dataStoreModule = module {
    single<SampleDatastore> { SampleDatastoreImpl(prefs = get()) }
    single<TokenDataSource> { TokenDataSourceImpl(dataStore = get()) }
}