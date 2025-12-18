package com.whatever.caramel.external.datastore.di

import com.whatever.caramel.core.datasource.local.LocalAppDataSource
import com.whatever.caramel.core.datasource.local.LocalCoupleDataSource
import com.whatever.caramel.core.datasource.local.LocalTokenDataSource
import com.whatever.caramel.core.datasource.local.LocalUserDataSource
import com.whatever.caramel.external.datastore.datasource.LocalAppDataSourceImpl
import com.whatever.caramel.external.datastore.datasource.LocalCoupleDataSourceImpl
import com.whatever.caramel.external.datastore.datasource.LocalTokenDataSourceImpl
import com.whatever.caramel.external.datastore.datasource.LocalUserDataSourceImpl
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
