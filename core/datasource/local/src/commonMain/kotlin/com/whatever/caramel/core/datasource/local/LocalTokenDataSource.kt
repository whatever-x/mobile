package com.whatever.caramel.core.datasource.local

interface LocalTokenDataSource {
    suspend fun saveToken(
        accessToken: String,
        refreshToken: String,
    )

    suspend fun deleteToken()

    suspend fun fetchAccessToken(): String

    suspend fun fetchRefreshToken(): String
}
