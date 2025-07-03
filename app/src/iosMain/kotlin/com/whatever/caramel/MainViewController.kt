@file:OptIn(ExperimentalNativeApi::class)

package com.whatever.caramel

import androidx.compose.ui.window.ComposeUIViewController
import androidx.navigation.compose.rememberNavController
import com.whatever.caramel.app.CaramelComposeApp
import com.whatever.caramel.di.initKoin
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
fun mainViewController() =
    ComposeUIViewController(
        configure = { initKoin() },
    ) {
        if (Platform.isDebugBinary) {
            Napier.base(DebugAntilog())
        }

        val navHostController = rememberNavController()

        CaramelComposeApp(
            navHostController = navHostController,
        )
    }
