package com.whatever.caramel.feature.login.mvi

import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface LoginIntent : UiIntent {

    data object ClickKakaoLoginButton : LoginIntent

    data object ClickAppleLoginButton : LoginIntent

}