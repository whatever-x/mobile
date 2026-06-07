package com.whatever.caramel.feature.balancegame.di

import com.whatever.caramel.feature.balancegame.history.BalanceGameHistoryViewModel
import com.whatever.caramel.feature.balancegame.share.BalanceGameShareViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

expect val imageShareModule: Module

val balanceGameFeatureModule =
    module {
        includes(imageShareModule)
        viewModelOf(::BalanceGameHistoryViewModel)
        viewModelOf(::BalanceGameShareViewModel)
    }
