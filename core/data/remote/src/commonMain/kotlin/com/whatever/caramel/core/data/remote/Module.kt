package com.whatever.caramel.core.data.remote

import com.whatever.caramel.core.data.remote.network.HttpClientFactory
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClientFactory.create(
            isDebug = NetworkConfig.isDebug,
            engine = get(),
            baseUrl = NetworkConfig.BASE_URL
        )
    }
}