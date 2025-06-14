package com.whatever.caramel.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.whatever.caramel.feature.content.create.navigation.contentCreateScreen
import com.whatever.caramel.feature.content.create.navigation.navigateToContentCreate
import com.whatever.caramel.feature.content.detail.navigation.contentDetailScreen
import com.whatever.caramel.feature.content.detail.navigation.navigateToContentDetail
import com.whatever.caramel.feature.content.edit.navigation.contentEditScreen
import com.whatever.caramel.feature.content.edit.navigation.navigateToContentEdit
import com.whatever.caramel.feature.copule.connecting.navigation.connectingScreen
import com.whatever.caramel.feature.copule.invite.navigation.inviteCoupleScreen
import com.whatever.caramel.feature.couple.connect.navigation.connectCoupleScreen
import com.whatever.caramel.feature.couple.connect.navigation.navigateToConnectCouple
import com.whatever.caramel.feature.login.navigation.loginScreen
import com.whatever.caramel.feature.login.navigation.navigateToLogin
import com.whatever.caramel.feature.main.navigation.mainGraph
import com.whatever.caramel.feature.main.navigation.navigateToMain
import com.whatever.caramel.feature.profile.create.navigation.ProfileCreateRoute
import com.whatever.caramel.feature.profile.create.navigation.createProfileScreen
import com.whatever.caramel.feature.profile.edit.mvi.ProfileEditType
import com.whatever.caramel.feature.profile.edit.navigation.editProfileScreen
import com.whatever.caramel.feature.profile.edit.navigation.navigateToEditProfile
import com.whatever.caramel.feature.setting.navigation.SettingRoute
import com.whatever.caramel.feature.setting.navigation.navigateToSetting
import com.whatever.caramel.feature.setting.navigation.settingScreen
import com.whatever.caramel.feature.splash.navigation.SplashRoute
import com.whatever.caramel.feature.splash.navigation.splashScreen
import com.whatever.caramel.mvi.AppIntent

@Composable
internal fun CaramelNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    onIntent: (AppIntent) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = SplashRoute
    ) {
        with(navHostController) {
            splashScreen(
                navigateToStartDestination = { userStatus ->
                    onIntent(AppIntent.NavigateToStartDestination(userStatus = userStatus))
                },
                navigateToLogin = {
                    navigateToLogin {
                        popUpTo(route = SplashRoute) {
                            inclusive = true
                        }
                    }
                },
            )
            loginScreen(
                navigateToStartDestination = { userStatus ->
                    onIntent(AppIntent.NavigateToStartDestination(userStatus = userStatus))
                },
            )
            createProfileScreen(
                navigateToLogin = {
                    navigateToLogin {
                        popUpTo(route = ProfileCreateRoute) {
                            inclusive = true
                        }
                    }
                },
                navigateToStartDestination = { userStatus ->
                    onIntent(AppIntent.NavigateToStartDestination(userStatus = userStatus))
                },
            )
            connectingScreen(
                navigateToMain = { navigateToMain() }
            )
            connectCoupleScreen(
                navigateToMain = { navigateToMain() },
                navigateToInviteCouple = { popBackStack() }
            )
            inviteCoupleScreen(
                navigateToConnectCouple = { navigateToConnectCouple() },
                navigateToLogin = { navigateToLogin() }
            )
            settingScreen(
                navigateToHome = { popBackStack() },
                navigateToEditBirthday = { birthday ->
                    navigateToEditProfile(
                        editType = ProfileEditType.BIRTHDAY,
                        birthday = birthday
                    )
                },
                navigateToEditNickName = { nickname ->
                    navigateToEditProfile(
                        editType = ProfileEditType.NICKNAME,
                        nickname = nickname
                    )
                },
                navigateToLogin = {
                    navigateToLogin {
                        popUpTo(SettingRoute) {
                            inclusive = true
                        }
                    }
                },
                navigateToEditCountDown = { startDate ->
                    navigateToEditProfile(
                        editType = ProfileEditType.START_DATE,
                        startDate = startDate
                    )
                }
            )
            contentCreateScreen(
                navigateToBackStack = { popBackStack() }
            )
            editProfileScreen(
                popBackStack = { popBackStack() }
            )
            mainGraph(
                navigateToSetting = { navigateToSetting() },
                navigateToStaredCoupleDay = {
                    navigateToEditProfile(
                        editType = ProfileEditType.START_DATE
                    )
                },
                navigateToTodoDetail = { contentId, contentType ->
                    navigateToContentDetail(contentId = contentId, type = contentType)
                },
                navigateToCreateTodo = { navigateToContentCreate(contentType = it) },
                navigateToCreateSchedule = { contentType, dateTimeString ->
                    navigateToContentCreate(
                        contentType = contentType,
                        dateTimeString = dateTimeString
                    )
                }
            )
            contentEditScreen(
                popBackStack = { popBackStack() }
            )
            contentDetailScreen(
                popBackStack = { popBackStack() },
                navigateToEdit = { id, type ->
                    navigateToContentEdit(
                        id = id,
                        type = type,
                    )
                }
            )
        }
    }
}