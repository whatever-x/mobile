package com.whatever.caramel.core.datasource.remote

interface RemoteFirebaseControllerDataSource {
    suspend fun postFcmToken(token: String)
}
