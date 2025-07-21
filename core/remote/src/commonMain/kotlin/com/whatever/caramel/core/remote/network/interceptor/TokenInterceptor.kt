package com.whatever.caramel.core.remote.network.interceptor

interface TokenInterceptor {
    suspend fun getAccessToken(): String

    suspend fun getRefreshToken(): String

    suspend fun refresh(): Boolean
}
