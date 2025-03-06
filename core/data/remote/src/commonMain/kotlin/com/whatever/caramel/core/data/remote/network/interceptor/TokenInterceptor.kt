package com.whatever.caramel.core.data.remote.network.interceptor

interface TokenInterceptor {
    suspend fun getAccessToken(): String
    suspend fun getRefreshToken(): String
    suspend fun refreshAccessToken(): String
}