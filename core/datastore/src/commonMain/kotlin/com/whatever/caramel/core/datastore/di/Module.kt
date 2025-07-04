package com.whatever.caramel.core.datastore.di

import com.whatever.caramel.core.datastore.datasource.CoupleDataSource
import com.whatever.caramel.core.datastore.datasource.CoupleDataSourceImpl
import com.whatever.caramel.core.datastore.datasource.TokenDataSource
import com.whatever.caramel.core.datastore.datasource.TokenDataSourceImpl
import com.whatever.caramel.core.datastore.datasource.UserDataSource
import com.whatever.caramel.core.datastore.datasource.UserDataSourceImpl
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformDataStoreModule: Module

val dataStoreModule =
    module {
        single<TokenDataSource> { TokenDataSourceImpl(dataStore = get()) }
        single<UserDataSource> { UserDataSourceImpl(dataStore = get()) }
        single<CoupleDataSource> { CoupleDataSourceImpl(dataStore = get()) }
    }
