package com.whatever.caramel.feat.sample.presentation.mvi

import com.whatever.caramel.core.presentation.UiIntent

sealed interface SampleIntent : UiIntent {

    data object ClickButton : SampleIntent

}