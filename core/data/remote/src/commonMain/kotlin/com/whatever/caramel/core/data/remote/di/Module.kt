package com.whatever.caramel.core.data.remote.di

import com.whatever.caramel.core.data.remote.datasource.RemoteSampleDataSource
import com.whatever.caramel.core.data.remote.datasource.RemoteSampleDataSourceImpl
import org.koin.core.module.Module
import org.koin.dsl.module

expect val networkClientModule: Module

val remoteModule = module {
    single<RemoteSampleDataSource> { RemoteSampleDataSourceImpl(client = get()) }
}
