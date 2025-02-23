package com.whatever.caramel.feat.splash.presentation

import androidx.compose.runtime.Composable
import com.whatever.caramel.feat.splash.presentation.components.SplashBox
import com.whatever.caramel.feat.splash.presentation.mvi.SplashIntent
import com.whatever.caramel.feat.splash.presentation.mvi.SplashState

@Composable
internal fun SplashScreen(
    state: SplashState,
    onIntent: (SplashIntent) -> Unit
) {
    SplashBox()
}