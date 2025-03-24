package com.whatever.caramel.feature.onboarding

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.usecase.user.SetOnboardingCompletionUseCase
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.onboarding.mvi.OnboardingIntent
import com.whatever.caramel.feature.onboarding.mvi.OnboardingSideEffect
import com.whatever.caramel.feature.onboarding.mvi.OnboardingState

class OnboardingViewModel(
    private val setOnboardingCompletionUseCase: SetOnboardingCompletionUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<OnboardingState, OnboardingSideEffect, OnboardingIntent>(savedStateHandle) {

    override fun createInitialState(savedStateHandle: SavedStateHandle): OnboardingState {
        return OnboardingState()
    }

    override suspend fun handleIntent(intent: OnboardingIntent) {
        when (intent) {
            is OnboardingIntent.ClickNextButton -> {
                setOnboardingCompletionUseCase(true)
                postSideEffect(OnboardingSideEffect.NavigateToLogin)
            }
        }
    }
}