package com.whatever.caramel.core.data.di

import com.whatever.caramel.core.data.interceptor.TokenInterceptorImpl
import com.whatever.caramel.core.data.repository.AuthRepositoryImpl
import com.whatever.caramel.core.data.repository.SampleRepositoryImpl
import com.whatever.caramel.core.domain.auth.repository.AuthRepository
import com.whatever.caramel.core.domain.repository.SampleRepository
import com.whatever.caramel.core.remote.network.interceptor.TokenInterceptor
import org.koin.dsl.module

val networkInterceptorModule = module {
    single<TokenInterceptor> {
        TokenInterceptorImpl(
            tokenDataSource = get(),
            authDataSource = get()
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