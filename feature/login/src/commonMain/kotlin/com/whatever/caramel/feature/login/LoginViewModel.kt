package com.whatever.caramel.feature.login

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.auth.model.SocialLoginModel
import com.whatever.caramel.core.domain.auth.repository.AuthRepository
import com.whatever.caramel.core.domain.exception.CaramelException
import com.whatever.caramel.core.domain.exception.ErrorUiType
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.login.mvi.LoginIntent
import com.whatever.caramel.feature.login.mvi.LoginSideEffect
import com.whatever.caramel.feature.login.mvi.LoginState
import com.whatever.caramel.feature.login.social.SocialAuthResult
import com.whatever.caramel.feature.login.social.kakao.KakaoUser
import io.github.aakira.napier.Napier

class LoginViewModel(
    savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository,
) : BaseViewModel<LoginState, LoginSideEffect, LoginIntent>(savedStateHandle) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): LoginState {
        return LoginState()
    }

    override suspend fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.ClickKakaoLoginButton -> kakaoLogin(result = intent.result)
        }
    }

    private suspend fun kakaoLogin(result: SocialAuthResult<KakaoUser>) {
        when (result) {
            is SocialAuthResult.Success -> {
                Napier.d { "id 토큰 : ${result.data.idToken}" }

                authRepository.loginWithSocialPlatform(
                    SocialLoginModel.Kakao(idToken = result.data.idToken)
                )

                // 이미 프로필이 생성 여부에 따라 화면 이동 분기
                // 프로필 생성 or 커플 연결로 이동
                postSideEffect(LoginSideEffect.NavigateToCreateProfile)
            }

            is SocialAuthResult.Error -> {
                // 카카오측 서버 에러
                Napier.d { "카카오측 서버 에러" }
                throw CaramelException(
                    message = "카카오 서버 에러",
                    debugMessage = "카카오 서버 에러",
                    errorUiType = ErrorUiType.SNACK_BAR
                )
            }

            is SocialAuthResult.UserCancelled -> {
                // 사용자 취소
                Napier.d { "사용자 취소" }
            }
        }
    }

}