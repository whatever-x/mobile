package com.whatever.caramel.feat.profile.edit.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.whatever.caramel.feat.profile.edit.ProfileEditRoute
import com.whatever.caramel.feat.profile.edit.mvi.ProfileEditType
import kotlinx.serialization.Serializable

@Serializable
data class ProfileEditRoute(
    val editType: String
)

fun NavHostController.navigateToEditProfile(
    editType: ProfileEditType,
    navOptions: NavOptions? = null
) {
    navigate(
        route = ProfileEditRoute(
            editType = editType.name
        ),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.editProfileScreen(
    popBackStack: () -> Unit
) {
    composable<ProfileEditRoute>() {
        ProfileEditRoute(
            popBackStack = popBackStack
        )
    }
}