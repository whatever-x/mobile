package com.whatever.caramel.feature.onboarding.mvi

import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface OnboardingIntent : UiIntent {

    data object ClickNextButton : OnboardingIntent

}