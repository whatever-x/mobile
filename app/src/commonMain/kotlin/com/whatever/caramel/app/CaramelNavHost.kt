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
        startDestination = SplashRoute,
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
                showErrorDialog = { title, message ->
                    onIntent(AppIntent.ShowErrorDialog(title, message))
                },
                showErrorToast = { message ->
                    onIntent(AppIntent.ShowToast(message))
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
                showErrorToast = { message ->
                    onIntent(AppIntent.ShowToast(message))
                },
                showErrorDialog = { title, message ->
                    onIntent(AppIntent.ShowErrorDialog(title, message))
                },
            )
            connectingScreen(
                navigateToMain = { navigateToMain() },
            )
            connectCoupleScreen(
                navigateToMain = { navigateToMain() },
                navigateToInviteCouple = { popBackStack() },
                showErrorDialog = { title, message ->
                    onIntent(AppIntent.ShowErrorDialog(title, message))
                },
                showErrorToast = { message ->
                    onIntent(AppIntent.ShowToast(message))
                },
            )
            inviteCoupleScreen(
                navigateToConnectCouple = { navigateToConnectCouple() },
                navigateToLogin = { navigateToLogin() },
                showErrorDialog = { title, message ->
                    onIntent(AppIntent.ShowErrorDialog(title, message))
                },
                showErrorToast = { message ->
                    onIntent(AppIntent.ShowToast(message))
                },
            )
            settingScreen(
                navigateToHome = { popBackStack() },
                navigateToEditBirthday = { birthday ->
                    navigateToEditProfile(
                        editType = ProfileEditType.BIRTHDAY,
                        birthday = birthday,
                    )
                },
                navigateToEditNickName = { nickname ->
                    navigateToEditProfile(
                        editType = ProfileEditType.NICKNAME,
                        nickname = nickname,
                    )
                },
                navigateToLogin = {
                    navigateToLogin {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                },
                navigateToEditCountDown = { startDate ->
                    navigateToEditProfile(
                        editType = ProfileEditType.START_DATE,
                        startDate = startDate,
                    )
                },
                showErrorDialog = { message, description ->
                    onIntent(AppIntent.ShowErrorDialog(message, description))
                },
                showErrorToast = { message ->
                    onIntent(AppIntent.ShowToast(message))
                },
            )
            contentCreateScreen(
                navigateToBackStack = { popBackStack() },
                showErrorDialog = { title, message ->
                    onIntent(AppIntent.ShowErrorDialog(title, message))
                },
            )
            editProfileScreen(
                popBackStack = { popBackStack() },
                showErrorDialog = { title, message ->
                    onIntent(AppIntent.ShowErrorDialog(title, message))
                },
                showErrorToast = { message ->
                    onIntent(AppIntent.ShowToast(message))
                },
            )
            mainGraph(
                navigateToSetting = { navigateToSetting() },
                navigateToStaredCoupleDay = {
                    navigateToEditProfile(
                        editType = ProfileEditType.START_DATE,
                    )
                },
                navigateToTodoDetail = { contentId, contentType ->
                    navigateToContentDetail(contentId = contentId, type = contentType)
                },
                navigateToCreateTodo = { navigateToContentCreate(contentType = it) },
                navigateToCreateSchedule = { contentType, dateTimeString ->
                    navigateToContentCreate(
                        contentType = contentType,
                        dateTimeString = dateTimeString,
                    )
                },
                showErrorDialog = { title, message ->
                    onIntent(AppIntent.ShowErrorDialog(title, message))
                },
                showErrorToast = { message ->
                    onIntent(AppIntent.ShowToast(message))
                },
            )
            contentEditScreen(
                popBackStack = { popBackStack() },
                showErrorDialog = { title, message ->
                    onIntent(AppIntent.ShowErrorDialog(title, message))
                },
            )
            contentDetailScreen(
                popBackStack = { popBackStack() },
                navigateToEdit = { id, type ->
                    navigateToContentEdit(
                        id = id,
                        type = type,
                    )
                },
                showErrorDialog = { title, message ->
                    onIntent(AppIntent.ShowErrorDialog(title, message))
                },
            )
        }
    }
}
