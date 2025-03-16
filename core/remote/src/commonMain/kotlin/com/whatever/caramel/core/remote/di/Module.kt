package com.whatever.caramel.core.remote.di

import com.whatever.caramel.core.remote.datasource.RemoteAuthDataSource
import com.whatever.caramel.core.remote.datasource.RemoteAuthDataSourceImpl
import com.whatever.caramel.core.remote.datasource.RemoteSampleDataSource
import com.whatever.caramel.core.remote.datasource.RemoteSampleDataSourceImpl
import com.whatever.caramel.core.remote.network.HttpClientFactory
import org.koin.core.module.Module
import org.koin.dsl.module

expect val networkClientModule: Module

val remoteModule = module {
    single<RemoteSampleDataSource> { RemoteSampleDataSourceImpl(client = get()) }
    single<RemoteAuthDataSource> { RemoteAuthDataSourceImpl(client = get()) }
    single { HttpClientFactory.create(engine = get()) }
}
