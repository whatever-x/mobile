package com.whatever.caramel.feat.main

import cafe.adriel.voyager.core.registry.screenModule
import com.whatever.caramel.app.Route

val mainNavigator = screenModule {
    register<Route.Main> {
        MainScreenRoot()
    }
}