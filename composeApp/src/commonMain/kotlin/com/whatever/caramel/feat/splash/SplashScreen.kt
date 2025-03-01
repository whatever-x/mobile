package com.whatever.caramel.feat.splash

import androidx.compose.runtime.Composable
import com.whatever.caramel.feat.splash.components.SplashBox
import com.whatever.caramel.feat.splash.mvi.SplashIntent
import com.whatever.caramel.feat.splash.mvi.SplashState

@Composable
internal fun SplashScreen(
    state: SplashState,
    onIntent: (SplashIntent) -> Unit
) {
    SplashBox()
}