package com.whatever.caramel.feat.setting.mvi

import com.whatever.caramel.core.presentation.UiState

data class SettingState(
    val text: String = ""
) : UiState