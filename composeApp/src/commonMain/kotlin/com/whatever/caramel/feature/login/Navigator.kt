package com.whatever.caramel.feature.login

import cafe.adriel.voyager.core.registry.screenModule
import com.whatever.caramel.app.Route

val loginNavigator = screenModule {
    register<Route.Login> {
        LoginScreenRoot()
    }
}