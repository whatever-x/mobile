package com.whatever.caramel.feature.login

import androidx.lifecycle.SavedStateHandle
import caramel.feature.login.generated.resources.Res
import caramel.feature.login.generated.resources.error_social_login
import caramel.feature.login.generated.resources.error_user_canceled
import com.whatever.caramel.core.domain.entity.auth.SocialLoginType
import com.whatever.caramel.core.domain.entity.user.UserStatus
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.usecase.auth.SignInWithSocialPlatformUseCase
import com.whatever.caramel.core.domain.usecase.auth.SocialLoginInputModel
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.login.mvi.LoginIntent
import com.whatever.caramel.feature.login.mvi.LoginSideEffect
import com.whatever.caramel.feature.login.mvi.LoginState
import com.whatever.caramel.feature.login.social.SocialAuthResult
import com.whatever.caramel.feature.login.social.apple.AppleUser
import com.whatever.caramel.feature.login.social.kakao.KakaoUser
import org.jetbrains.compose.resources.getString

class LoginViewModel(
    savedStateHandle: SavedStateHandle,
    private val signInWithSocialPlatformUseCase: SignInWithSocialPlatformUseCase,
) : BaseViewModel<LoginState, LoginSideEffect, LoginIntent>(savedStateHandle) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): LoginState {
        return LoginState()
    }

    override suspend fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.ClickKakaoLoginButton -> kakaoLogin(result = intent.result)
            is LoginIntent.ClickAppleLoginButton -> appleLogin(result = intent.result)
        }
    }

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)
        val message = if (throwable is CaramelException) {
            throwable.message
        } else {
            "Unknown error occurred"
        }
        postSideEffect(LoginSideEffect.ShowErrorSnackBar(message))
    }

    private suspend fun kakaoLogin(result: SocialAuthResult<KakaoUser>) {
        when (result) {
            is SocialAuthResult.Success -> {
                login(
                    socialLoginInputModel = SocialLoginInputModel(
                        idToken = result.data.idToken,
                        socialLoginType = SocialLoginType.KAKAO
                    )
                )
            }

            is SocialAuthResult.Error -> {
                postSideEffect(LoginSideEffect.ShowErrorSnackBar(getString(Res.string.error_social_login)))
            }

            is SocialAuthResult.UserCancelled -> {
                postSideEffect(LoginSideEffect.ShowErrorSnackBar(getString(Res.string.error_user_canceled)))
            }
        }
    }

    private suspend fun appleLogin(result: SocialAuthResult<AppleUser>) {
        when (result) {
            is SocialAuthResult.Success -> {
                login(
                    socialLoginInputModel = SocialLoginInputModel(
                        idToken = result.data.idToken,
                        socialLoginType = SocialLoginType.APPLE
                    )
                )
            }

            is SocialAuthResult.Error -> {
                postSideEffect(LoginSideEffect.ShowErrorSnackBar(getString(Res.string.error_social_login)))
            }

            is SocialAuthResult.UserCancelled -> {
                postSideEffect(LoginSideEffect.ShowErrorSnackBar(getString(Res.string.error_user_canceled)))
            }
        }
    }

    private suspend fun login(
        socialLoginInputModel: SocialLoginInputModel
    ) {
        val userStatus = signInWithSocialPlatformUseCase(
            inputModel = socialLoginInputModel
        )
        when (userStatus) {
            UserStatus.NONE -> {}
            UserStatus.NEW -> postSideEffect(LoginSideEffect.NavigateToCreateProfile)
            UserStatus.SINGLE -> postSideEffect(LoginSideEffect.NavigateToConnectCouple)
            UserStatus.COUPLED -> postSideEffect(LoginSideEffect.NavigateToMain)
        }
    }
}