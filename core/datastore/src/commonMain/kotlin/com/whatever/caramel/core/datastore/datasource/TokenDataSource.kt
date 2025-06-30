package com.whatever.caramel.core.datastore.datasource

interface TokenDataSource {
    suspend fun createToken(
        accessToken: String,
        refreshToken: String,
    )

    suspend fun deleteToken()

    suspend fun fetchAccessToken(): String

    suspend fun fetchRefreshToken(): String
}
