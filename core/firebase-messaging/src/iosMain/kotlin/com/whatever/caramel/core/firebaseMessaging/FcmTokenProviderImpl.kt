package com.whatever.caramel.core.firebaseMessaging

import com.whatever.caramel.core.remote.datasource.RemoteFirebaseControllerDataSource
import firebaseMessagingBridge.FcmTokenBridge
import kotlinx.cinterop.ExperimentalForeignApi
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalForeignApi::class)
class FcmTokenProviderImpl(
    private val fcmTokenBridge: FcmTokenBridge,
    private val remoteFirebaseControllerDataSource: RemoteFirebaseControllerDataSource,
) : FcmTokenProvider {
    override suspend fun updateToken() {
        val token =
            suspendCoroutine { cont ->
                fcmTokenBridge.requestTokenWithCompletion { token ->
                    cont.resume(token)
                }
            }

        if (!token.isNullOrBlank()) {
            remoteFirebaseControllerDataSource.postFcmToken(token = token)
        } else {
            println("❌ FCM 토큰이 null 또는 빈 문자열입니다.")
        }
    }

    override suspend fun updateToken(token: String) {
        remoteFirebaseControllerDataSource.postFcmToken(token = token)
    }
}

// 토큰을 획득하는 방법은 두 가지
// 1. onNewToken 콜백 함수
// 새로운 토큰이 생성되면 자동으로 콜백이 실행된다.
// 2. FirebaseMessaging.getInstance().token
// 파이어베이스 초기화 이후에 언제든지 현재 토큰을 가져올 수 있다.
// 토큰을 업데이트 해야 하는 시점
// 1. 로그인 성공 시
// 로그인 성공시 현재 토큰을 가져와 토큰을 앱 서버에 갱신한다. -> Android 구현 가능 / iOS 구현 가능
// 2. 새로운 토큰 생성 시
// 새 토큰을 발급 받으면 곧바로 토큰을 앱 서버에 갱신한다. -> Android 구현 가능 / iOS 구현 가능
// 3. 앱이 메인 화면의 포그라운드로 돌아올 시
// 현재 토큰을 조회하고 토큰을 앱 서버에 갱신한다. -> 로그인처럼 구현 가능
