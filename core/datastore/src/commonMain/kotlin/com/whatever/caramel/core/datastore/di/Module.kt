package com.whatever.caramel.core.datastore.di

import com.whatever.caramel.core.datastore.datasource.LocalAppDataSource
import com.whatever.caramel.core.datastore.datasource.LocalAppDataSourceImpl
import com.whatever.caramel.core.datastore.datasource.LocalCoupleDataSource
import com.whatever.caramel.core.datastore.datasource.LocalCoupleDataSourceImpl
import com.whatever.caramel.core.datastore.datasource.LocalTokenDataSource
import com.whatever.caramel.core.datastore.datasource.LocalTokenDataSourceImpl
import com.whatever.caramel.core.datastore.datasource.LocalUserDataSource
import com.whatever.caramel.core.datastore.datasource.LocalUserDataSourceImpl
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformDataStoreModule: Module

val dataStoreModule =
    module {
        single<LocalTokenDataSource> { LocalTokenDataSourceImpl(dataStore = get()) }
        single<LocalUserDataSource> { LocalUserDataSourceImpl(dataStore = get()) }
        single<LocalCoupleDataSource> { LocalCoupleDataSourceImpl(dataStore = get()) }
        single<LocalAppDataSource> { LocalAppDataSourceImpl(dataStore = get()) }
    }
