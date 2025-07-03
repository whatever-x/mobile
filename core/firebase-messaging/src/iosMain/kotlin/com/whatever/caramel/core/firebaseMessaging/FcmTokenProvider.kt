package com.whatever.caramel.core.firebaseMessaging

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual interface FcmTokenProvider {
    actual suspend fun updateToken()

    actual suspend fun updateToken(token: String)
}
