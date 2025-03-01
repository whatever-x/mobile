package com.whatever.caramel.feat.profile.presentation.edit.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.whatever.caramel.feat.profile.presentation.edit.ProfileEditRoute
import com.whatever.caramel.feat.profile.presentation.edit.mvi.ProfileEditType
import kotlinx.serialization.Serializable

@Serializable
data class ProfileEditRoute(
    val editType: ProfileEditType
)

fun NavHostController.navigateToEditProfile(
    editType: ProfileEditType,
    navOptions: NavOptions? = null
) {
    navigate(
        route = ProfileEditRoute(
            editType = editType
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