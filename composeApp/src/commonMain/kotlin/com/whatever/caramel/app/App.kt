package com.whatever.caramel.app

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.whatever.caramel.feature.couple.coupleScreenNavigator
import com.whatever.caramel.feature.home.homeNavigator
import com.whatever.caramel.feature.login.loginNavigator
import com.whatever.caramel.feature.onboarding.onboardingNavigator
import com.whatever.caramel.feature.profile.profileNavigator
import com.whatever.caramel.feature.splash.SplashScreenRoot

@Composable
fun App() {
    MaterialTheme {
        Surface(
            modifier = Modifier.systemBarsPadding()
        ) {
            ScreenRegistry {
                onboardingNavigator()
                coupleScreenNavigator()
                homeNavigator()
                loginNavigator()
                profileNavigator()
            }
            Navigator(
                screen = SplashScreenRoot()
            ) { navigator: Navigator ->
                SlideTransition(navigator)
            }
        }
    }
}

