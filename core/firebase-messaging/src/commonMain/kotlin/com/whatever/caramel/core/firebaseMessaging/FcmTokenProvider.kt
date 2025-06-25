package com.whatever.caramel.core.firebaseMessaging

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect interface FcmTokenProvider {

    suspend fun updateToken()

    suspend fun updateToken(token: String)

}