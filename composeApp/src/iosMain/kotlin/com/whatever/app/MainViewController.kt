package com.whatever.app

import androidx.compose.ui.window.ComposeUIViewController
import com.whatever.app.app.App
import com.whatever.app.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }