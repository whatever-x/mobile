package com.whatever.caramel.feature.onboarding.mvi

import com.whatever.caramel.core.viewmodel.UiState

data class OnboardingState(
    val text: String = ""
) : UiState