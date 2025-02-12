package com.whatever.caramel.feature.couple

import cafe.adriel.voyager.core.registry.screenModule
import com.whatever.caramel.app.Route
import com.whatever.caramel.feature.couple.code.CoupleCodeScreenRoot
import com.whatever.caramel.feature.couple.invite.CoupleInviteScreenRoot

val coupleScreenNavigator = screenModule {
    register<Route.CoupleCode> { CoupleCodeScreenRoot() }
    register<Route.CoupleInvite> { CoupleInviteScreenRoot() }
}