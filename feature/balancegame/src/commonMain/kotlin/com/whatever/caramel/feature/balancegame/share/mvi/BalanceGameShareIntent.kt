package com.whatever.caramel.feature.balancegame.share.mvi

import com.whatever.caramel.core.viewmodel.UiIntent

sealed interface BalanceGameShareIntent : UiIntent {
    data object ClickBackButton : BalanceGameShareIntent
}
