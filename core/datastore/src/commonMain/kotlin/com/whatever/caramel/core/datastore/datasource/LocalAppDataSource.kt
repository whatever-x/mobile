package com.whatever.caramel.core.datastore.datasource

interface LocalAppDataSource {
    suspend fun saveAppLaunchCount(count: Int)

    suspend fun fetchAppLaunchCount(): Int
}
