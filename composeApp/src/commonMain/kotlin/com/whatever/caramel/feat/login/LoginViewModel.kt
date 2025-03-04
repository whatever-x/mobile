package com.whatever.caramel.feat.login

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.CaramelException
import com.whatever.caramel.core.domain.ErrorUiType
import com.whatever.caramel.core.presentation.BaseViewModel
import com.whatever.caramel.feat.login.mvi.LoginIntent
import com.whatever.caramel.feat.login.mvi.LoginSideEffect
import com.whatever.caramel.feat.login.mvi.LoginState
import com.whatever.caramel.feat.login.social.SocialAuthResult
import com.whatever.caramel.feat.login.social.kakao.KakaoUser
import io.github.aakira.napier.Napier

class LoginViewModel(
    savedStateHandle: SavedStateHandle,
    // @ham2174 TODO : 로컬에 사용자 프로필 데이터 유무 로직 구현
) : BaseViewModel<LoginState, LoginSideEffect, LoginIntent>(savedStateHandle) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): LoginState {
        return LoginState()
    }

    override suspend fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.ClickKakaoLoginButton -> kakaoLogin(result = intent.result)
        }
    }

    private fun kakaoLogin(result: SocialAuthResult<KakaoUser>) {
        when (result) {
            is SocialAuthResult.Success -> {
                // 1. 액세스 토큰 우리 서버로 보내기
                // 2. 로그인 성공시 다음 화면 진입
                Napier.d { "액세스 토큰 : ${result.data.accessToken}" }
                result.data.accessToken
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