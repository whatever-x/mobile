package com.whatever.caramel.feat.onboarding.presentation.mvi

import com.whatever.caramel.core.presentation.UiState

data class OnboardingState(
    val text: String = ""
) : UiState