package com.whatever.caramel.feature.login.social.kakao

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.whatever.caramel.feature.login.social.SocialAuthResult
import com.whatever.caramel.feature.login.social.SocialAuthenticator
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class KakaoAuthProviderImpl : KakaoAuthProvider {
    @Composable
    override fun get(): SocialAuthenticator<KakaoUser> {
        val context = LocalContext.current
        return KakaoAuthenticator(context = context)
    }
}

private class KakaoAuthenticator(
    private val context: Context,
) : SocialAuthenticator<KakaoUser> {
    init {
        KakaoSdk.init(context, context.getKakaoNativeAppKey())
    }

    override suspend fun authenticate(): SocialAuthResult<KakaoUser> =
        suspendCancellableCoroutine { cont ->
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                    cont.handle(token, error)
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                    cont.handle(token, error)
                }
            }
        }

    private fun CancellableContinuation<SocialAuthResult<KakaoUser>>.handle(
        token: OAuthToken?,
        error: Throwable?,
    ) {
        if (error != null) {
            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                resume(SocialAuthResult.UserCancelled)
            } else {
                resume(SocialAuthResult.Error)
            }
        } else {
            val idToken = token?.idToken
            if (idToken.isNullOrEmpty()) {
                resume(SocialAuthResult.Error)
            } else {
                resume(SocialAuthResult.Success(KakaoUser(idToken)))
            }
        }
    }
}

private fun Context.getKakaoNativeAppKey(): String {
    val applicationInfo =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getApplicationInfo(
                packageName,
                PackageManager.ApplicationInfoFlags.of(PackageManager.GET_META_DATA.toLong()),
            )
        } else {
            @Suppress("DEPRECATION")
            packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        }

    return applicationInfo.metaData?.getString("com.whatever.caramel.KAKAO_NATIVE_APP_KEY")
        ?: error("Missing Kakao native app key in AndroidManifest.xml.")
}
