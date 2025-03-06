package com.whatever.caramel.feature.setting.mvi

import com.whatever.caramel.core.viewmodel.UiState

data class SettingState(
    val text: String = ""
) : UiState