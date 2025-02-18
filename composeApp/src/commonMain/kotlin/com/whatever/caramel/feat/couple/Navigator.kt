package com.whatever.caramel.feat.couple

import cafe.adriel.voyager.core.registry.screenModule
import com.whatever.caramel.app.Route
import com.whatever.caramel.feat.couple.code.CoupleCodeScreenRoot
import com.whatever.caramel.feat.couple.invite.CoupleInviteScreenRoot

val coupleScreenNavigator = screenModule {
    register<Route.CoupleCode> { CoupleCodeScreenRoot() }
    register<Route.CoupleInvite> { CoupleInviteScreenRoot() }
}