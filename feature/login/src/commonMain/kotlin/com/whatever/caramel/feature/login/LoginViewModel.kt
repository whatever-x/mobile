package com.whatever.caramel.feature.login

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.crashlytics.CaramelCrashlytics
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.ErrorUiType
import com.whatever.caramel.core.domain.usecase.auth.SignInWithSocialPlatformUseCase
import com.whatever.caramel.core.domain.vo.auth.SocialLoginType
import com.whatever.caramel.core.firebaseMessaging.FcmTokenProvider
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.login.mvi.LoginIntent
import com.whatever.caramel.feature.login.mvi.LoginSideEffect
import com.whatever.caramel.feature.login.mvi.LoginState
import com.whatever.caramel.feature.login.social.SocialAuthResult
import com.whatever.caramel.feature.login.social.apple.AppleUser
import com.whatever.caramel.feature.login.social.kakao.KakaoUser

class LoginViewModel(
    savedStateHandle: SavedStateHandle,
    crashlytics: CaramelCrashlytics,
    private val signInWithSocialPlatformUseCase: SignInWithSocialPlatformUseCase,
    private val fcmTokenProvider: FcmTokenProvider,
) : BaseViewModel<LoginState, LoginSideEffect, LoginIntent>(savedStateHandle, crashlytics) {
    override fun createInitialState(savedStateHandle: SavedStateHandle): LoginState = LoginState

    override suspend fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.ClickKakaoLoginButton -> kakaoLogin(result = intent.result)
            is LoginIntent.ClickAppleLoginButton -> appleLogin(result = intent.result)
        }
    }

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)
        if (throwable is CaramelException) {
            when (throwable.errorUiType) {
                ErrorUiType.TOAST ->
                    postSideEffect(
                        LoginSideEffect.ShowErrorToast(
                            message = throwable.message,
                        ),
                    )
                ErrorUiType.DIALOG ->
                    postSideEffect(
                        LoginSideEffect.ShowErrorDialog(
                            message = throwable.message,
                            description = throwable.description,
                        ),
                    )
            }
        } else {
            caramelCrashlytics.recordException(throwable)
            postSideEffect(
                LoginSideEffect.ShowErrorDialog(
                    message = "알 수 없는 오류가 발생했습니다.",
                    description = null,
                ),
            )
        }
    }

    private suspend fun kakaoLogin(result: SocialAuthResult<KakaoUser>) {
        when (result) {
            is SocialAuthResult.Success -> {
                login(
                    idToken = result.data.idToken,
                    socialLoginType = SocialLoginType.KAKAO,
                )
            }

            is SocialAuthResult.Error -> {
                postSideEffect(LoginSideEffect.ShowErrorToast("로그인에 실패하였습니다."))
            }

            is SocialAuthResult.UserCancelled -> {
                postSideEffect(LoginSideEffect.ShowErrorToast("취소되었습니다."))
            }
        }
    }

    private suspend fun appleLogin(result: SocialAuthResult<AppleUser>) {
        when (result) {
            is SocialAuthResult.Success -> {
                login(
                    idToken = result.data.idToken,
                    socialLoginType = SocialLoginType.APPLE,
                )
            }

            is SocialAuthResult.Error -> {
                postSideEffect(LoginSideEffect.ShowErrorToast("로그인에 실패하였습니다."))
            }

            is SocialAuthResult.UserCancelled -> {
                postSideEffect(LoginSideEffect.ShowErrorToast("취소되었습니다."))
            }
        }
    }

    private suspend fun login(
        idToken: String,
        socialLoginType: SocialLoginType,
    ) {
        launch {
            val userStatus =
                signInWithSocialPlatformUseCase(
                    idToken = idToken,
                    socialLoginType = socialLoginType,
                )
            fcmTokenProvider.updateToken()
            postSideEffect(LoginSideEffect.NavigateToStartDestination(userStatus = userStatus))
        }
    }
}
