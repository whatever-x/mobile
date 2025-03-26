package com.whatever.caramel.core.data.di

import com.whatever.caramel.core.data.interceptor.TokenInterceptorImpl
import com.whatever.caramel.core.data.repository.AuthRepositoryImpl
import com.whatever.caramel.core.data.repository.CoupleRepositoryImpl
import com.whatever.caramel.core.data.repository.UserRepositoryImpl
import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.repository.CoupleRepository
import com.whatever.caramel.core.domain.repository.UserRepository
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
    single<AuthRepository> {
        AuthRepositoryImpl(
            remoteAuthDataSource = get(),
            tokenDataSource = get()
        )
    }

    single<UserRepository> {
        UserRepositoryImpl(
            userRemoteDataSource = get(),
            userDataSource = get()
        )
    }

    single<CoupleRepository> {
        CoupleRepositoryImpl(
            coupleRemoteDataSource = get(),
        )
    }
}