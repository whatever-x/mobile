package com.whatever.caramel.feature.profile

import cafe.adriel.voyager.core.registry.screenModule
import com.whatever.caramel.app.Route
import com.whatever.caramel.feature.profile.birth.ProfileBirthScreenRoot
import com.whatever.caramel.feature.profile.nickname.ProfileNicknameScreenRoot
import com.whatever.caramel.feature.profile.terms.ProfileTermsScreenRoot

val profileNavigator = screenModule {
    register<Route.ProfileBirth> {
        ProfileBirthScreenRoot()
    }

    register<Route.ProfileNickName> {
        ProfileNicknameScreenRoot()
    }

    register<Route.ProfileTerms> {
        ProfileTermsScreenRoot()
    }
}