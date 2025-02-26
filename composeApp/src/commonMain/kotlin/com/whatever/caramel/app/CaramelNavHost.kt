package com.whatever.caramel.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import com.whatever.caramel.feat.couple.presentation.navigation.coupleGraph
import com.whatever.caramel.feat.couple.presentation.navigation.navigateToCoupleInvite
import com.whatever.caramel.feat.login.presentation.navigation.loginScreen
import com.whatever.caramel.feat.login.presentation.navigation.navigateToLogin
import com.whatever.caramel.feat.main.presentation.navigation.mainGraph
import com.whatever.caramel.feat.main.presentation.navigation.navigateToMain
import com.whatever.caramel.feat.onboarding.presentation.navigation.navigateToOnboarding
import com.whatever.caramel.feat.onboarding.presentation.navigation.onboardingScreen
import com.whatever.caramel.feat.profile.presentation.navigation.createProfileScreen
import com.whatever.caramel.feat.profile.presentation.navigation.navigateToCreateProfile
import com.whatever.caramel.feat.setting.navigation.navigateToSetting
import com.whatever.caramel.feat.setting.navigation.settingScreen
import com.whatever.caramel.feat.splash.presentation.navigation.SplashRoute
import com.whatever.caramel.feat.splash.presentation.navigation.splashScreen

@Composable
internal fun CaramelNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = SplashRoute
    ) {
        with(navHostController) {
            splashScreen(
                navigateToLogin = {},
                navigateToMain = {},
                navigateToOnBoarding = {
                    navigateToOnboarding(
                        navOptions = NavOptions.Builder()
                            .setPopUpTo<SplashRoute>(inclusive = true)
                            .build()
                    )
                }
            )
            onboardingScreen(
                navigateToLogin = { navigateToLogin() }
            )
            loginScreen(
                navigateToConnectCouple = {
                    navigateToCoupleInvite {
                        popUpTo(graph.id)
                    }
                },
                navigateToCreateProfile = { navigateToCreateProfile() }
            )
            createProfileScreen(
                navigateToLogin = { popBackStack() },
                navigateToConnectCouple = {
                    navigateToCoupleInvite {
                        popUpTo(graph.id)
                    }
                }
            )
            settingScreen(
                navigateToHome = { popBackStack() },
                navigateToEditBirthday = {},
                navigateToEditNickName = {}
            )
            coupleGraph(
                navHostController = this,
                navigateToMain = {
                    navigateToMain {
                        popUpTo(graph.id)
                    }
                }
            )
            mainGraph(
                navigateToSetting = { navigateToSetting() },
                navigateToStaredCoupleDay = {},
                navigateToTodoDetail = {},
                navigateToCreateTodo = {}
            )
        }
    }
}