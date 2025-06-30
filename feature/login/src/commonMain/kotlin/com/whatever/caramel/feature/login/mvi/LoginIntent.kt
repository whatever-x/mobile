package com.whatever.caramel.feature.login.mvi

import com.whatever.caramel.core.viewmodel.UiIntent
import com.whatever.caramel.feature.login.social.SocialAuthResult
import com.whatever.caramel.feature.login.social.apple.AppleUser
import com.whatever.caramel.feature.login.social.kakao.KakaoUser

sealed interface LoginIntent : UiIntent {
    data class ClickKakaoLoginButton(val result: SocialAuthResult<KakaoUser>) : LoginIntent

    data class ClickAppleLoginButton(val result: SocialAuthResult<AppleUser>) : LoginIntent
}
