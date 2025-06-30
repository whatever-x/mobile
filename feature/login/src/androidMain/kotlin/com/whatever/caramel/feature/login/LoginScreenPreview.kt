package com.whatever.caramel.feature.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.login.mvi.LoginState

@Composable
@Preview
private fun LoginScreenPreview() {
    CaramelTheme {
        LoginScreen(
            state = LoginState,
            onIntent = {},
            onLaunch = {},
        )
    }
}
