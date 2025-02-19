package com.whatever.caramel.app

import cafe.adriel.voyager.core.registry.ScreenProvider
import com.whatever.caramel.feat.splash.SplashViewModel

sealed class Route : ScreenProvider {
    data class Splash(val viewModel : SplashViewModel) : Route()
    data object Onboarding : Route()
    data object Login : Route()
    data object ProfileBirth : Route()
    data object ProfileNickName : Route()
    data object ProfileTerms : Route()
    data object CoupleCode : Route()
    data object CoupleInvite : Route()
    data object Main : Route()
}