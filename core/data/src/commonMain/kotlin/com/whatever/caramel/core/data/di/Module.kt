package com.whatever.caramel.core.data.di

import com.whatever.caramel.core.data.interceptor.TokenInterceptorImpl
import com.whatever.caramel.core.data.remote.network.HttpClientFactory
import com.whatever.caramel.core.data.remote.interceptor.TokenInterceptor
import com.whatever.caramel.core.data.repository.SampleRepositoryImpl
import com.whatever.caramel.core.domain.repository.SampleRepository
import org.koin.dsl.module

val networkModule = module {
    single<TokenInterceptor> {
        TokenInterceptorImpl(
            localSampleDataSource = get()
        )
    }

    single {
        HttpClientFactory.create(
            engine = get(),
            tokenInterceptor = get()
        )
    }
}

val repositoryModule = module {
    single<SampleRepository> {
        SampleRepositoryImpl(
            remoteSampleDataSource = get(),
            localSampleDataSource = get(),
            sampleDao = get()
        )
    }
}