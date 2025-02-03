package com.whatever.caramel

import androidx.compose.ui.window.ComposeUIViewController
import com.whatever.caramel.app.App
import com.whatever.caramel.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }