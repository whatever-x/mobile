package com.whatever.caramel.app

import androidx.compose.runtime.Composable
import com.whatever.caramel.core.designSystem.themes.CaramelTheme
import com.whatever.caramel.feat.sample.presentation.SampleRoute

@Composable
fun App() {
    CaramelTheme {
        SampleRoute()
    }
}

