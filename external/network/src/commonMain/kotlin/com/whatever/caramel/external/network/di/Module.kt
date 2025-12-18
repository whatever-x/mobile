package com.whatever.caramel.external.network.di

import com.whatever.caramel.core.datasource.remote.LinkMetadataRemoteDataSource
import com.whatever.caramel.core.datasource.remote.RemoteAppDataSource
import com.whatever.caramel.core.datasource.remote.RemoteAuthDataSource
import com.whatever.caramel.core.datasource.remote.RemoteBalanceGameDataSource
import com.whatever.caramel.core.datasource.remote.RemoteCalendarDataSource
import com.whatever.caramel.core.datasource.remote.RemoteCoupleDataSource
import com.whatever.caramel.core.datasource.remote.RemoteFirebaseControllerDataSource
import com.whatever.caramel.core.datasource.remote.RemoteMemoDataSource
import com.whatever.caramel.core.datasource.remote.RemoteScheduleDataSource
import com.whatever.caramel.core.datasource.remote.RemoteTagDataSource
import com.whatever.caramel.core.datasource.remote.RemoteUserDataSource
import com.whatever.caramel.external.network.HttpClientFactory
import com.whatever.caramel.external.network.config.addDeviceIdHeader
import com.whatever.caramel.external.network.config.addTimeZoneHeader
import com.whatever.caramel.external.network.config.caramelDefaultRequest
import com.whatever.caramel.external.network.config.caramelResponseValidator
import com.whatever.caramel.external.network.datasource.LinkMetadataRemoteDataSourceImpl
import com.whatever.caramel.external.network.datasource.RemoteAppDataSourceImpl
import com.whatever.caramel.external.network.datasource.RemoteAuthDataSourceImpl
import com.whatever.caramel.external.network.datasource.RemoteBalanceGameDataSourceImpl
import com.whatever.caramel.external.network.datasource.RemoteCalendarDataSourceImpl
import com.whatever.caramel.external.network.datasource.RemoteCoupleDatsSourceImpl
import com.whatever.caramel.external.network.datasource.RemoteFirebaseControllerDataSourceImpl
import com.whatever.caramel.external.network.datasource.RemoteMemoDataSourceImpl
import com.whatever.caramel.external.network.datasource.RemoteScheduleDataSourceImpl
import com.whatever.caramel.external.network.datasource.RemoteTagDataSourceImpl
import com.whatever.caramel.external.network.datasource.RemoteUserDataSourceImpl
import com.whatever.caramel.external.network.di.qualifier.AuthClient
import com.whatever.caramel.external.network.di.qualifier.DefaultClient
import com.whatever.caramel.external.network.interceptor.TokenInterceptor
import com.whatever.caramel.external.network.interceptor.TokenInterceptorImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import org.koin.core.module.Module
import org.koin.dsl.module

expect val networkClientEngineModule: Module
expect val deviceIdModule: Module

val networkModule =
    module {
        single { HttpClientFactory.create(engine = get()) }
        single<TokenInterceptor> { TokenInterceptorImpl(get(), get()) }

        single(DefaultClient) {
            get<HttpClient>().config {
                addDeviceIdHeader(get())
                addTimeZoneHeader()
                caramelResponseValidator()
                caramelDefaultRequest()
            }
        }

        single(AuthClient) {
            get<HttpClient>(DefaultClient).config {
                install(Auth) {
                    bearer {
                        loadTokens {
                            val accessToken = get<TokenInterceptor>().getAccessToken()

                            if (accessToken.isNotEmpty()) {
                                BearerTokens(accessToken, null)
                            } else {
                                null
                            }
                        }

                        refreshTokens {
                            val refreshed = get<TokenInterceptor>().refresh()

                            if (refreshed) {
                                val accessToken = get<TokenInterceptor>().getAccessToken()
                                val refreshToken = get<TokenInterceptor>().getRefreshToken()

                                if (accessToken.isNotEmpty() && refreshToken.isNotEmpty()) {
                                    BearerTokens(accessToken, refreshToken)
                                } else {
                                    null
                                }
                            } else {
                                null
                            }
                        }
                    }
                }
            }
        }
    }

val remoteDataSourceModule =
    module {
        single<RemoteAppDataSource> {
            RemoteAppDataSourceImpl(
                defaultClient = get(DefaultClient),
            )
        }

        single<RemoteAuthDataSource> {
            RemoteAuthDataSourceImpl(
                defaultClient = get(DefaultClient),
                authClient = get(AuthClient),
            )
        }

        single<RemoteUserDataSource> {
            RemoteUserDataSourceImpl(
                authClient = get(AuthClient),
            )
        }

        single<RemoteCoupleDataSource> {
            RemoteCoupleDatsSourceImpl(
                authClient = get(AuthClient),
            )
        }

        single<RemoteCalendarDataSource> {
            RemoteCalendarDataSourceImpl(
                authClient = get(AuthClient),
            )
        }

        single<RemoteMemoDataSource> {
            RemoteMemoDataSourceImpl(
                authClient = get(AuthClient),
            )
        }

        single<RemoteScheduleDataSource> {
            RemoteScheduleDataSourceImpl(
                authClient = get(AuthClient),
            )
        }

        single<RemoteTagDataSource> {
            RemoteTagDataSourceImpl(
                authClient = get(AuthClient),
            )
        }

        single<RemoteBalanceGameDataSource> {
            RemoteBalanceGameDataSourceImpl(
                authClient = get(AuthClient),
            )
        }

        single<LinkMetadataRemoteDataSource> {
            LinkMetadataRemoteDataSourceImpl(
                httpClient = get(DefaultClient),
            )
        }

        single<RemoteFirebaseControllerDataSource> {
            RemoteFirebaseControllerDataSourceImpl(
                authClient = get(AuthClient),
            )
        }
    }
