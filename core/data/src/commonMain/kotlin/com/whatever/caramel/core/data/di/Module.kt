package com.whatever.caramel.core.data.di

import com.whatever.caramel.core.data.interceptor.TokenInterceptorImpl
import com.whatever.caramel.core.data.repository.AuthRepositoryImpl
import com.whatever.caramel.core.data.repository.BalanceGameRepositoryImpl
import com.whatever.caramel.core.data.repository.CalendarRepositoryImpl
import com.whatever.caramel.core.data.repository.ContentRepositoryImpl
import com.whatever.caramel.core.data.repository.CoupleRepositoryImpl
import com.whatever.caramel.core.data.repository.MemoRepositoryImpl
import com.whatever.caramel.core.data.repository.ScheduleRepositoryImpl
import com.whatever.caramel.core.data.repository.UserRepositoryImpl
import com.whatever.caramel.core.domain.repository.AuthRepository
import com.whatever.caramel.core.domain.repository.BalanceGameRepository
import com.whatever.caramel.core.domain.repository.CalendarRepository
import com.whatever.caramel.core.domain.repository.ContentRepository
import com.whatever.caramel.core.domain.repository.CoupleRepository
import com.whatever.caramel.core.domain.repository.MemoRepository
import com.whatever.caramel.core.domain.repository.ScheduleRepository
import com.whatever.caramel.core.domain.repository.UserRepository
import com.whatever.caramel.core.remote.network.interceptor.TokenInterceptor
import org.koin.dsl.module

val networkInterceptorModule =
    module {
        single<TokenInterceptor> {
            TokenInterceptorImpl(
                localTokenDataSource = get(),
                remoteAuthDataSource = get(),
            )
        }
    }

val repositoryModule =
    module {
        single<AuthRepository> {
            AuthRepositoryImpl(
                remoteAuthDataSource = get(),
                localTokenDataSource = get(),
            )
        }

        single<UserRepository> {
            UserRepositoryImpl(
                remoteUserDataSource = get(),
                localUserDataSource = get(),
            )
        }

        single<CoupleRepository> {
            CoupleRepositoryImpl(
                localCoupleDataSource = get(),
                remoteCoupleDataSource = get(),
            )
        }

        single<CalendarRepository> {
            CalendarRepositoryImpl(
                remoteCalendarDataSource = get(),
                remoteCoupleDataSource = get(),
            )
        }

        single<ContentRepository> {
            ContentRepositoryImpl(
                remoteLinkMetadataRemoteDataSource = get(),
                remoteTagDataSource = get(),
            )
        }

        single<MemoRepository> {
            MemoRepositoryImpl(
                remoteMemoDataSource = get(),
            )
        }

        single<ScheduleRepository> {
            ScheduleRepositoryImpl(
                remoteScheduleDataSource = get(),
            )
        }

        single<BalanceGameRepository> {
            BalanceGameRepositoryImpl(
                remoteBalanceGameDataSource = get(),
            )
        }
    }
