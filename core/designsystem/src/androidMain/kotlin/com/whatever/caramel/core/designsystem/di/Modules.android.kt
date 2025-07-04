package com.whatever.caramel.core.designsystem.di

import com.whatever.caramel.core.designsystem.util.AndroidHapticController
import com.whatever.caramel.core.designsystem.util.HapticController
import org.koin.core.module.Module
import org.koin.dsl.module

actual val hapticControllerModule: Module =
    module {
        single<HapticController> { AndroidHapticController(context = get()) }
    }
