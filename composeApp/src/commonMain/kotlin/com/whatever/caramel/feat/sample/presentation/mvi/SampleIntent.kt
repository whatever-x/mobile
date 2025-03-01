package com.whatever.caramel.feat.sample.presentation.mvi

import com.whatever.caramel.core.presentation.UiIntent
import com.whatever.caramel.feat.sample.domain.SampleModel

sealed interface SampleIntent : UiIntent {

    data object ClickButton : SampleIntent
    data object GetLocal : SampleIntent
    data class SaveLocal(val sampleModel : SampleModel) : SampleIntent

}