package com.whatever.caramel.external.firebaseMessaging

import com.google.firebase.messaging.FirebaseMessaging
import com.whatever.caramel.core.datasource.remote.RemoteFirebaseControllerDataSource
import kotlinx.coroutines.tasks.await

class FcmTokenProviderImpl(
    private val remoteFirebaseControllerDataSource: RemoteFirebaseControllerDataSource,
) : FcmTokenProvider {
    override suspend fun updateToken() {
        val fcmToken = FirebaseMessaging.getInstance().token.await()

        remoteFirebaseControllerDataSource.postFcmToken(token = fcmToken)
    }

    override suspend fun updateToken(token: String) {
        remoteFirebaseControllerDataSource.postFcmToken(token = token)
    }
}
