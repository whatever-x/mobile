package com.whatever.caramel.core.datastore.datasource

interface UserDataSource {
    suspend fun getUserStatus() : String
    suspend fun setUserStatus(state: String)
    suspend fun deleteUserStatus()
}