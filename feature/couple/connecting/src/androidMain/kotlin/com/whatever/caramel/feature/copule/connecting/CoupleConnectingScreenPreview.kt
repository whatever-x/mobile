package com.whatever.caramel.feature.copule.connecting

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.copule.connecting.mvi.CoupleConnectingState

@Composable
@Preview
private fun ConnectingScreenPreview() {
    CaramelTheme {
        CoupleConnectingScreen(
            state = CoupleConnectingState(),
            onIntent = {},
        )
    }
}
