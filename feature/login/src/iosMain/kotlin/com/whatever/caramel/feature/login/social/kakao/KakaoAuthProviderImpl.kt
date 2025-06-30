package com.whatever.caramel.feature.login.social.kakao

import androidx.compose.runtime.Composable
import com.whatever.caramel.feature.login.social.SocialAuthResult
import com.whatever.caramel.feature.login.social.SocialAuthenticator
import kotlinx.cinterop.ExperimentalForeignApi
import swiftBridge.KakaoLoginBridge
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalForeignApi::class)
internal class KakaoAuthProviderImpl(
    private val bridge: KakaoLoginBridge,
) : KakaoAuthProvider {
    @Composable
    override fun get(): SocialAuthenticator<KakaoUser> = KakaoAuthenticatorImpl(bridge)
}

@OptIn(ExperimentalForeignApi::class)
private class KakaoAuthenticatorImpl(
    private val bridge: KakaoLoginBridge,
) : SocialAuthenticator<KakaoUser> {
    override suspend fun authenticate(): SocialAuthResult<KakaoUser> =
        suspendCoroutine { cont ->
            bridge.requestWithSuccess(
                success = {
                    if (it.isNullOrEmpty()) {
                        cont.resume(SocialAuthResult.Error)
                    } else {
                        cont.resume(SocialAuthResult.Success(KakaoUser(it)))
                    }
                },
                failure = {
                    cont.resume(SocialAuthResult.Error)
                },
                cancel = {
                    cont.resume(SocialAuthResult.UserCancelled)
                },
            )
        }
}
