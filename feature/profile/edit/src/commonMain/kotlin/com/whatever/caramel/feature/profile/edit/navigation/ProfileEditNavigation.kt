package com.whatever.caramel.feature.profile.edit.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.whatever.caramel.feature.profile.edit.ProfileEditRoute
import com.whatever.caramel.feature.profile.edit.mvi.ProfileEditType
import kotlinx.serialization.Serializable

@Serializable
data class ProfileEditRoute(
    val editType: String,
    val nickname: String,
    val birthdayMillisecond: Long,
    val startDateMillisecond: Long
)

fun NavHostController.navigateToEditProfile(
    editType: ProfileEditType,
    nickname: String = "",
    birthdayMillisecond: Long = 0L,
    startDateMillisecond: Long = 0L,
    navOptions: NavOptions? = null
) {
    navigate(
        route = ProfileEditRoute(
            editType = editType.name,
            nickname = nickname,
            birthdayMillisecond = birthdayMillisecond,
            startDateMillisecond = startDateMillisecond
        ),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.editProfileScreen(
    popBackStack: () -> Unit
) {
    composable<ProfileEditRoute> {
        ProfileEditRoute(
            popBackStack = popBackStack
        )
    }
}