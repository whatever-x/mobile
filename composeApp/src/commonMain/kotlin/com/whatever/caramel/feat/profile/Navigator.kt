package com.whatever.caramel.feat.profile

import cafe.adriel.voyager.core.registry.screenModule
import com.whatever.caramel.app.Route
import com.whatever.caramel.feat.profile.birth.ProfileBirthScreenRoot
import com.whatever.caramel.feat.profile.nickname.ProfileNicknameScreenRoot
import com.whatever.caramel.feat.profile.terms.ProfileTermsScreenRoot

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