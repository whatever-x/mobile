package com.whatever.caramel.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.whatever.caramel.feature.content.navigation.contentScreen
import com.whatever.caramel.feature.content.navigation.navigateToContent
import com.whatever.caramel.feature.copule.invite.navigation.inviteCoupleScreen
import com.whatever.caramel.feature.copule.invite.navigation.navigateToInviteCouple
import com.whatever.caramel.feature.couple.connect.navigation.connectCoupleScreen
import com.whatever.caramel.feature.couple.connect.navigation.navigateToConnectCouple
import com.whatever.caramel.feature.login.navigation.loginScreen
import com.whatever.caramel.feature.login.navigation.navigateToLogin
import com.whatever.caramel.feature.main.navigation.mainGraph
import com.whatever.caramel.feature.main.navigation.navigateToMain
import com.whatever.caramel.feature.profile.create.navigation.createProfileScreen
import com.whatever.caramel.feature.profile.create.navigation.navigateToCreateProfile
import com.whatever.caramel.feature.profile.edit.mvi.ProfileEditType
import com.whatever.caramel.feature.profile.edit.navigation.editProfileScreen
import com.whatever.caramel.feature.profile.edit.navigation.navigateToEditProfile
import com.whatever.caramel.feature.setting.navigation.SettingRoute
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
                navigateToInviteCouple = {
                    navigateToInviteCouple {
                        popUpTo(route = SplashRoute) {
                            inclusive = true
                        }
                    }
                },
                navigateToCreateProfile = {
                    navigateToCreateProfile {
                        popUpTo(route = SplashRoute) {
                            inclusive = true
                        }
                    }
                }
            )
            loginScreen(
                navigateToConnectCouple = { navigateToInviteCouple() },
                navigateToCreateProfile = { navigateToCreateProfile() },
                navigateToMain = { navigateToMain() }
            )
            createProfileScreen(
                navigateToLogin = { navigateToLogin() },
                navigateToConnectCouple = { navigateToInviteCouple() }
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
                },
                navigateToLogin = {
                    navigateToLogin {
                        popUpTo(SettingRoute) {
                            inclusive = true
                        }
                    }
                },
                navigateToEditCountDown = {
                    navigateToEditProfile(
                        editType = ProfileEditType.D_DAY
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