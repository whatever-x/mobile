package com.whatever.caramel.core.data.interceptor

import com.whatever.caramel.core.data.datastore.LocalSampleDataSource
import com.whatever.caramel.core.data.remote.datasource.RemoteSampleDataSource
import com.whatever.caramel.core.data.remote.interceptor.TokenInterceptor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TokenInterceptorImpl(
    private val localSampleDataSource: LocalSampleDataSource,
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