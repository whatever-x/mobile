package com.whatever.caramel.feature.balancegame.di

import com.whatever.caramel.feature.balancegame.history.BalanceGameHistoryViewModel
import com.whatever.caramel.feature.balancegame.share.BalanceGameShareViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val balanceGameFeatureModule =
    module {
        viewModelOf(::BalanceGameHistoryViewModel)
        viewModelOf(::BalanceGameShareViewModel)
    }
