package com.whatever.caramel.feat.sample.presentation.mvi

import com.whatever.caramel.core.presentation.UiSideEffect

sealed interface SampleSideEffect : UiSideEffect {

    data object ShowSnackBar : SampleSideEffect

}