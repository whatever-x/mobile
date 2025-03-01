package com.whatever.caramel.feat.sample.presentation.mvi

import com.whatever.caramel.core.presentation.UiState
import com.whatever.caramel.feat.sample.domain.SampleModel

data class SampleUiState(
    val networkResultData : SampleModel? = null,
    val networkResult: String = "",
    val localResult: String = "",
    val saveNameResult : String = ""
) : UiState
