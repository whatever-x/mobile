package com.whatever.caramel.feat.login

import cafe.adriel.voyager.core.registry.screenModule
import com.whatever.caramel.app.Route

val loginNavigator = screenModule {
    register<Route.Login> {
        LoginScreenRoot()
    }
}