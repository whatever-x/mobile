package com.whatever.caramel.core.data.di

import com.whatever.caramel.core.data.interceptor.TokenInterceptorImpl
import com.whatever.caramel.core.data.repository.AuthRepositoryImpl
import com.whatever.caramel.core.remote.network.HttpClientFactory
import com.whatever.caramel.core.remote.network.interceptor.TokenInterceptor
import com.whatever.caramel.core.data.repository.SampleRepositoryImpl
import com.whatever.caramel.core.domain.auth.repository.AuthRepository
import com.whatever.caramel.core.domain.repository.SampleRepository
import org.koin.dsl.module

val networkModule = module {
    single<TokenInterceptor> {
        TokenInterceptorImpl(
            sampleDatastore = get()
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
            sampleDatastore = get(),
            sampleDao = get()
        )
    }

    single<AuthRepository> {
        AuthRepositoryImpl(
            remoteAuthDataSource = get()
        )
    }

}