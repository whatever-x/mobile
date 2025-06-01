package com.whatever.caramel.core.firebaseMessaging

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class CaramelFirebaseMessagingService : FirebaseMessagingService() {

    private val fcmTokenProvider: FcmTokenProvider by inject()
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        scope.launch {
            try {
                fcmTokenProvider.updateToken(token = token)
            } catch (e: Exception) {
                Napier.e { "FCM 토큰 업데이트 실패: ${e.message}" }
            }
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Napier.d { "메세지 리시브 발동" }

        val notification = message.notification

        if (notification != null) {
            Napier.d { "타이틀 : " + notification.title }
            Napier.d { "바디 : " + notification.body }
        }
    }

}

// 토큰을 보내야하는 시점
    // 로그인 이후 호출 - LoginViewModel 호출 / O
    // 새로운 토큰이 발급되었을 때 호출 - onNewToken / O
    // 앱 실행할 때 마다 호출 - MainViewModel 호출 / O

// FcmProvider 역할
    // 현재 토큰을 앱 서버로 전달하는 로직

