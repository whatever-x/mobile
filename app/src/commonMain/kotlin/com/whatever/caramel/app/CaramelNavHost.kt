package com.whatever.caramel.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import com.whatever.caramel.feature.content.navigation.contentScreen
import com.whatever.caramel.feature.content.navigation.navigateToContent
import com.whatever.caramel.feature.couple.connect.navigation.connectCoupleScreen
import com.whatever.caramel.feature.couple.connect.navigation.navigateToConnectCouple
import com.whatever.caramel.feature.copule.invite.navigation.inviteCoupleScreen
import com.whatever.caramel.feature.copule.invite.navigation.navigateToCoupleInvite
import com.whatever.caramel.feature.login.navigation.loginScreen
import com.whatever.caramel.feature.login.navigation.navigateToLogin
import com.whatever.caramel.feature.main.navigation.mainGraph
import com.whatever.caramel.feature.main.navigation.navigateToMain
import com.whatever.caramel.feature.onboarding.navigation.navigateToOnboarding
import com.whatever.caramel.feature.onboarding.navigation.onboardingScreen
import com.whatever.caramel.feature.profile.create.navigation.createProfileScreen
import com.whatever.caramel.feature.profile.create.navigation.navigateToCreateProfile
import com.whatever.caramel.feature.profile.edit.mvi.ProfileEditType
import com.whatever.caramel.feature.profile.edit.navigation.editProfileScreen
import com.whatever.caramel.feature.profile.edit.navigation.navigateToEditProfile
import com.whatever.caramel.feature.setting.navigation.navigateToSetting
import com.whatever.caramel.feature.setting.navigation.settingScreen
import com.whatever.caramel.feature.splash.navigation.SplashRoute
import com.whatever.caramel.feature.splash.navigation.splashScreen

@Composable
internal fun CaramelNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = SplashRoute
    ) {
        with(navHostController) {
            splashScreen(
                navigateToLogin = { navigateToLogin() },
                navigateToMain = { navigateToMain() },
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
                navigateToConnectCouple = { navigateToCoupleInvite() },
                navigateToCreateProfile = { navigateToCreateProfile() }
            )
            createProfileScreen(
                navigateToLogin = { navigateToLogin() },
                navigateToConnectCouple = { navigateToCoupleInvite() }
            )
            settingScreen(
                navigateToHome = { popBackStack() },
                navigateToEditBirthday = {
                    navigateToEditProfile(
                        editType = ProfileEditType.BIRTHDAY
                    )
                },
                navigateToEditNickName = {
                    navigateToEditProfile(
                        editType = ProfileEditType.NICK_NAME
                    )
                }
            )
            contentScreen(
                navigateToBackStack = { popBackStack() }
            )
            editProfileScreen(
                popBackStack = { popBackStack() }
            )
            inviteCoupleScreen(
                navigateToConnectCouple = { navigateToConnectCouple() },
                navigateToLogin = { navigateToLogin() }
            )
            connectCoupleScreen(
                navigateToMain = { navigateToMain() },
                navigateToInviteCouple = { popBackStack() }
            )
            mainGraph(
                navigateToSetting = { navigateToSetting() },
                navigateToStaredCoupleDay = {
                    navigateToEditProfile(
                        editType = ProfileEditType.D_DAY
                    )
                },
                navigateToTodoDetail = { navigateToContent() },
                navigateToCreateTodo = { navigateToContent() }
            )
        }
    }
}