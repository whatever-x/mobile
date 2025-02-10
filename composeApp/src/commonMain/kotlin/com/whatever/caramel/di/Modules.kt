package com.whatever.caramel.di

import com.whatever.caramel.core.data.HttpClientFactory
import com.whatever.caramel.core.data.NetworkConfig
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single {
        HttpClientFactory.create(
            engine = get(),
            baseUrl = NetworkConfig.BASE_URL
        )
    }
}