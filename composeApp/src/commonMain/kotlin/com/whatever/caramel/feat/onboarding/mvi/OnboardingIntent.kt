package com.whatever.caramel.feat.onboarding.mvi

import com.whatever.caramel.core.presentation.UiIntent

sealed interface OnboardingIntent : UiIntent {

    data object ClickNextButton : OnboardingIntent

}