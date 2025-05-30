package com.whatever.caramel.core.data.di

import com.whatever.caramel.core.data.interceptor.TokenInterceptorImpl
import com.whatever.caramel.core.data.repository.AuthRepositoryImpl
import com.whatever.caramel.core.data.repository.BalanceGameRepositoryImpl
import com.whatever.caramel.core.data.repository.CalendarRepositoryImpl
import com.whatever.caramel.core.data.repository.ContentRepositoryImpl
import com.whatever.caramel.core.data.repository.CoupleRepositoryImpl
import com.whatever.caramel.core.data.repository.UserRepositoryImpl
import com.whatever.caramel.core.data.repository.TagRepositoryImpl
import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.repository.BalanceGameRepository
import com.whatever.caramel.core.domain.repository.CalendarRepository
import com.whatever.caramel.core.domain.repository.ContentRepository
import com.whatever.caramel.core.domain.repository.CoupleRepository
import com.whatever.caramel.core.domain.repository.UserRepository
import com.whatever.caramel.core.domain.repository.TagRepository
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
            localCoupleDataSource = get(),
            remoteCoupleDataSource = get()
        )
    }

    single<CalendarRepository> {
        CalendarRepositoryImpl(
            remoteCalendarDataSource = get()
        )
    }

    single<ContentRepository> {
        ContentRepositoryImpl(
            remoteContentDataSource = get()
        )
    }

    single<TagRepository> {
        TagRepositoryImpl(
            remoteTagDataSource = get()
        )
    }

    single<BalanceGameRepository> {
        BalanceGameRepositoryImpl(
            remoteBalanceGameDataSource = get()
        )
    }
}