package com.whatever.caramel.core.remote.datasource

interface RemoteFirebaseControllerDataSource {

    suspend fun postFcmToken(token: String)

}