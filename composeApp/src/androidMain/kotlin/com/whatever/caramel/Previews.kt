package com.whatever.caramel

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.whatever.caramel.feat.splash.SplashState
import com.whatever.caramel.feat.splash.SplashScreen

@Preview
@Composable
private fun SplashScreenPreview() {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme
    ){
        SplashScreen(
            state = SplashState()
        )
    }
}