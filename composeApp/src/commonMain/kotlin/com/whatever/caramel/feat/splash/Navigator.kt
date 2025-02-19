package com.whatever.caramel.feat.splash

import cafe.adriel.voyager.core.registry.screenModule
import com.whatever.caramel.app.Route

val splashNavigator = screenModule {
    register<Route.Splash> {
        SplashScreenRoot(it.viewModel)
    }
}