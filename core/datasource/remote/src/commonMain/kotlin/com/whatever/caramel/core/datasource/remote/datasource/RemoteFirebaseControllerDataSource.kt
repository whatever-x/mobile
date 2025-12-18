package com.whatever.caramel.core.datasource.remote.datasource

interface RemoteFirebaseControllerDataSource {
    suspend fun postFcmToken(token: String)
}
