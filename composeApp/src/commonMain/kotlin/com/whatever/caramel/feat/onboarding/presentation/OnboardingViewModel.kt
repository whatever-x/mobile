package com.whatever.caramel.feat.onboarding.presentation

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.presentation.BaseViewModel
import com.whatever.caramel.feat.onboarding.presentation.mvi.OnboardingIntent
import com.whatever.caramel.feat.onboarding.presentation.mvi.OnboardingSideEffect
import com.whatever.caramel.feat.onboarding.presentation.mvi.OnboardingState

class OnboardingViewModel(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<OnboardingState, OnboardingSideEffect, OnboardingIntent>(savedStateHandle) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): OnboardingState {
        return OnboardingState()
    }

    override suspend fun handleIntent(intent: OnboardingIntent) {
        when (intent) {
            is OnboardingIntent.ClickNextButton -> postSideEffect(OnboardingSideEffect.NavigateToLogin)
        }
    }

}