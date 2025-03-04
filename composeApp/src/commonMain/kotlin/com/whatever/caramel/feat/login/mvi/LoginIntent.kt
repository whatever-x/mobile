package com.whatever.caramel.feat.login.mvi

import com.whatever.caramel.core.presentation.UiIntent
import com.whatever.caramel.feat.login.social.SocialAuthResult
import com.whatever.caramel.feat.login.social.kakao.KakaoUser

sealed interface LoginIntent : UiIntent {

    data class ClickKakaoLoginButton(val result: SocialAuthResult<KakaoUser>) : LoginIntent

}