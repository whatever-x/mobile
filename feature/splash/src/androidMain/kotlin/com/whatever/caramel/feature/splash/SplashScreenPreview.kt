package com.whatever.caramel.feature.splash

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.splash.mvi.SplashState

@Composable
@Preview
private fun SplashScreenPreview() {
    CaramelTheme {
        SplashScreen(
            state = SplashState(),
            onIntent = {}
        )
    }
}