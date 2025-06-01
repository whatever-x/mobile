package com.whatever.caramel.core.remote.datasource

import com.whatever.caramel.core.remote.dto.firebase.request.FcmTokenRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.koin.core.annotation.Named

class RemoteFirebaseControllerDataSourceImpl(
    @Named("AuthClient") private val authClient: HttpClient
): RemoteFirebaseControllerDataSource {

    override suspend fun postFcmToken(token: String) {
        authClient.post(FIREBASE_CONTROLLER_BASE_URL + "fcm") {
            setBody(body = FcmTokenRequest(token = token))
        }
    }

    companion object {
        private const val FIREBASE_CONTROLLER_BASE_URL = "/v1/firebase/"
    }
}