package com.whatever.caramel.core.remote.network.interceptor

interface TokenInterceptor {

    suspend fun getAuthToken() : Pair<String?, String?>

    suspend fun refresh(): Boolean

}