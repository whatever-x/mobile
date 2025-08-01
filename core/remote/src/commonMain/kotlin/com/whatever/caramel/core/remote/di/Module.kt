package com.whatever.caramel.core.remote.di

import com.whatever.caramel.core.remote.datasource.LinkMetadataRemoteDataSource
import com.whatever.caramel.core.remote.datasource.LinkMetadataRemoteDataSourceImpl
import com.whatever.caramel.core.remote.datasource.RemoteAuthDataSource
import com.whatever.caramel.core.remote.datasource.RemoteAuthDataSourceImpl
import com.whatever.caramel.core.remote.datasource.RemoteBalanceGameDataSource
import com.whatever.caramel.core.remote.datasource.RemoteBalanceGameDataSourceImpl
import com.whatever.caramel.core.remote.datasource.RemoteCalendarDataSource
import com.whatever.caramel.core.remote.datasource.RemoteCalendarDataSourceImpl
import com.whatever.caramel.core.remote.datasource.RemoteCoupleDataSource
import com.whatever.caramel.core.remote.datasource.RemoteCoupleDatsSourceImpl
import com.whatever.caramel.core.remote.datasource.RemoteFirebaseControllerDataSource
import com.whatever.caramel.core.remote.datasource.RemoteFirebaseControllerDataSourceImpl
import com.whatever.caramel.core.remote.datasource.RemoteMemoDataSource
import com.whatever.caramel.core.remote.datasource.RemoteMemoDataSourceImpl
import com.whatever.caramel.core.remote.datasource.RemoteTagDataSource
import com.whatever.caramel.core.remote.datasource.RemoteTagDataSourceImpl
import com.whatever.caramel.core.remote.datasource.RemoteUserDataSource
import com.whatever.caramel.core.remote.datasource.RemoteUserDataSourceImpl
import com.whatever.caramel.core.remote.di.qualifier.AuthClient
import com.whatever.caramel.core.remote.di.qualifier.DefaultClient
import com.whatever.caramel.core.remote.network.HttpClientFactory
import com.whatever.caramel.core.remote.network.config.addDeviceIdHeader
import com.whatever.caramel.core.remote.network.config.addTimeZoneHeader
import com.whatever.caramel.core.remote.network.config.caramelDefaultRequest
import com.whatever.caramel.core.remote.network.config.caramelResponseValidator
import com.whatever.caramel.core.remote.network.interceptor.TokenInterceptor
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
