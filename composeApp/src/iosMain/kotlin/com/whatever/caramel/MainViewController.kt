@file:OptIn(ExperimentalNativeApi::class)

package com.whatever.caramel

import androidx.compose.ui.window.ComposeUIViewController
import com.whatever.caramel.app.App
import com.whatever.caramel.core.data.NetworkConfig
import com.whatever.caramel.di.initKoin
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlin.experimental.ExperimentalNativeApi

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) {
    if (Platform.isDebugBinary) {
        Napier.base(DebugAntilog())
    }

    App()
}