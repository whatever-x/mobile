package com.whatever.caramel.feature.splash

import androidx.compose.runtime.Composable
import com.whatever.caramel.feature.splash.components.SplashBox
import com.whatever.caramel.feature.splash.mvi.SplashIntent
import com.whatever.caramel.feature.splash.mvi.SplashState

@Composable
internal fun SplashScreen(
    state: SplashState,
    onIntent: (SplashIntent) -> Unit
) {
    SplashBox()
}