package com.whatever.caramel.feat.login.presentation.mvi

import com.whatever.caramel.core.presentation.UiIntent

sealed interface LoginIntent : UiIntent {

    data object ClickKakaoLoginButton : LoginIntent

    data object ClickAppleLoginButton : LoginIntent

}