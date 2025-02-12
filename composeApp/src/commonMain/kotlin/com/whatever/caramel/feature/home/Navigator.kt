package com.whatever.caramel.feature.home

import cafe.adriel.voyager.core.registry.screenModule
import com.whatever.caramel.app.Route

val homeNavigator = screenModule {
    register<Route.Home> {
        HomeScreenRoot()
    }
}