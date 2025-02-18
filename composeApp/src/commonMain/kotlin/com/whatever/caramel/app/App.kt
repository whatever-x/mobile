package com.whatever.caramel.app

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.whatever.caramel.feat.couple.coupleScreenNavigator
import com.whatever.caramel.feat.login.loginNavigator
import com.whatever.caramel.feat.main.mainNavigator
import com.whatever.caramel.feat.onboarding.onboardingNavigator
import com.whatever.caramel.feat.profile.profileNavigator
import com.whatever.caramel.feat.splash.SplashScreenRoot

@Composable
fun App() {
    MaterialTheme {
        Surface(
            modifier = Modifier.systemBarsPadding()
        ) {
            ScreenRegistry {
                onboardingNavigator()
                coupleScreenNavigator()
                mainNavigator()
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

