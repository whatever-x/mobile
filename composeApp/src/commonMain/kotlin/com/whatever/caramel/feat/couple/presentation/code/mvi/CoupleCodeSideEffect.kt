package com.whatever.caramel.feat.couple.presentation.code.mvi

import com.whatever.caramel.core.presentation.UiSideEffect

sealed interface CoupleCodeSideEffect : UiSideEffect {

    data object NavigateToHome : CoupleCodeSideEffect

}