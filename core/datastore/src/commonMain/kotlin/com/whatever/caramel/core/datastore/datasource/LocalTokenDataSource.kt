package com.whatever.caramel.core.datastore.datasource

interface LocalTokenDataSource {
    suspend fun saveToken(
        accessToken: String,
        refreshToken: String,
    )

    suspend fun deleteToken()

    suspend fun fetchAccessToken(): String

    suspend fun fetchRefreshToken(): String
}
