package com.whatever.caramel.feat.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.whatever.caramel.app.Route

data class SplashScreenRoot(
    val viewModel : SplashViewModel
) : Screen {
    @Composable
    override fun Content() {
        val state by viewModel.state.collectAsStateWithLifecycle()
        SplashScreen(
            state = state
        )
    }
}

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    state: SplashState
) {
    val navigator = LocalNavigator.currentOrThrow
    val onBoardingScreen = rememberScreen(Route.Onboarding)
    val loginScreen = rememberScreen(provider = Route.Login)
    val profileScreen = rememberScreen(provider = Route.ProfileBirth)
    val coupleScreen = rememberScreen(provider = Route.CoupleInvite)
    val mainScreen = rememberScreen(provider = Route.Main)

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Splash",
            style = TextStyle(
                color = Color.Black,
                fontSize = 24.sp
            ),
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        }
    }

    when {
        state.isFirst -> navigator.replace(onBoardingScreen)
        state.isSuccess -> navigator.replace(mainScreen)
        state.needLogin -> navigator.replace(loginScreen)
        state.needPermission -> {
            // do something..
        }
        state.needCouple -> navigator.replace(coupleScreen)
        state.needProfile -> navigator.replace(profileScreen)
    }
}