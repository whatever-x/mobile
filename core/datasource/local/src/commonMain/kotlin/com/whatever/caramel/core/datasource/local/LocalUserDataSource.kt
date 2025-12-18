package com.whatever.caramel.core.datasource.local

interface LocalUserDataSource {
    suspend fun fetchUserStatus(): String

    suspend fun saveUserStatus(state: String)

    suspend fun deleteUserStatus()
}
