package com.whatever.caramel.core.firebaseMessaging

interface FcmTokenProvider {

    suspend fun updateToken()

    suspend fun updateToken(token: String)

}
