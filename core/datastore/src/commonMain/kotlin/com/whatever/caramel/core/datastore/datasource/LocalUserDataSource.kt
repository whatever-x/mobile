package com.whatever.caramel.core.datastore.datasource

interface LocalUserDataSource {
    suspend fun fetchUserStatus(): String

    suspend fun saveUserStatus(state: String)

    suspend fun deleteUserStatus()
}
