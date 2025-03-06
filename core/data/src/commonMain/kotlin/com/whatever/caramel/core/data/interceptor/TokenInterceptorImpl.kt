package com.whatever.caramel.core.data.interceptor

import com.whatever.caramel.core.data.datastore.SampleDatastore
import com.whatever.caramel.core.data.remote.datasource.RemoteSampleDataSource
import com.whatever.caramel.core.data.remote.network.interceptor.TokenInterceptor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

// @RyuSw-cs 2025.03.06 TODO : 추후 토큰이 필요한 로직이 있다면 개발필요
class TokenInterceptorImpl(
    private val sampleDatastore: SampleDatastore,
) : TokenInterceptor, KoinComponent {

    private val sampleRemoteDataSource: RemoteSampleDataSource by inject()

    override suspend fun getAccessToken(): String {
        return ""
    }

    override suspend fun getRefreshToken(): String {
        return ""
    }

    override suspend fun refreshAccessToken(): String {
        return ""
    }
}