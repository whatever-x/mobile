package com.whatever.caramel.feature.balancegame.di

import com.whatever.caramel.feature.balancegame.share.image.ImageShareManager
import com.whatever.caramel.feature.balancegame.share.image.IosImageShareManager
import org.koin.core.module.Module
import org.koin.dsl.module

actual val imageShareModule: Module =
    module {
        single<ImageShareManager> { IosImageShareManager() }
    }
