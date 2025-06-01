package com.whatever.caramel.feature.login

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.code.AppErrorCode
import com.whatever.caramel.core.domain.exception.code.AuthErrorCode
import com.whatever.caramel.core.domain.usecase.auth.SignInWithSocialPlatformUseCase
import com.whatever.caramel.core.domain.vo.auth.SocialLoginType
import com.whatever.caramel.core.firebaseMessaging.datasource.FcmTokenProvider
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.login.mvi.LoginIntent
import com.whatever.caramel.feature.login.mvi.LoginSideEffect
import com.whatever.caramel.feature.login.mvi.LoginState
import com.whatever.caramel.feature.login.social.SocialAuthResult
import com.whatever.caramel.feature.login.social.apple.AppleUser
import com.whatever.caramel.feature.login.social.kakao.KakaoUser

class LoginViewModel(
    savedStateHandle: SavedStateHandle,
    private val signInWithSocialPlatformUseCase: SignInWithSocialPlatformUseCase,
    private val fcmTokenProvider: FcmTokenProvider,
) : BaseViewModel<LoginState, LoginSideEffect, LoginIntent>(savedStateHandle) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): LoginState {
        return LoginState
    }

    override suspend fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.ClickKakaoLoginButton -> kakaoLogin(result = intent.result)
            is LoginIntent.ClickAppleLoginButton -> appleLogin(result = intent.result)
        }
    }

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)
        val (code, message) = if (throwable is CaramelException) {
            throwable.code to throwable.message
        } else {
            AppErrorCode.UNKNOWN to null
        }
        postSideEffect(LoginSideEffect.ShowErrorSnackBar(code = code, message = message))
    }

    private suspend fun kakaoLogin(result: SocialAuthResult<KakaoUser>) {
        when (result) {
            is SocialAuthResult.Success -> {
                login(
                    idToken = result.data.idToken,
                    socialLoginType = SocialLoginType.KAKAO
                )
            }

            is SocialAuthResult.Error -> {
                postSideEffect(LoginSideEffect.ShowErrorSnackBar(code = AuthErrorCode.LOGIN_FAILED))
            }

            is SocialAuthResult.UserCancelled -> {
                postSideEffect(LoginSideEffect.ShowErrorSnackBar(code = AuthErrorCode.LOGIN_CANCELLED))
            }
        }
    }

    private suspend fun appleLogin(result: SocialAuthResult<AppleUser>) {
        when (result) {
            is SocialAuthResult.Success -> {
                login(
                    idToken = result.data.idToken,
                    socialLoginType = SocialLoginType.APPLE
                )
            }

            is SocialAuthResult.Error -> {
                postSideEffect(LoginSideEffect.ShowErrorSnackBar(code = AuthErrorCode.LOGIN_FAILED))
            }

            is SocialAuthResult.UserCancelled -> {
                postSideEffect(LoginSideEffect.ShowErrorSnackBar(code = AuthErrorCode.LOGIN_CANCELLED))
            }
        }
    }

    private suspend fun login(
        idToken: String,
        socialLoginType: SocialLoginType
    ) {
        launch {
            signInWithSocialPlatformUseCase(
                idToken = idToken,
                socialLoginType = socialLoginType
            )
            fcmTokenProvider.updateToken()
            postSideEffect(LoginSideEffect.NavigateToStartDestination)
        }
    }
}