package com.whatever.caramel.core.ui.admob

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier

val LocalMainGoogleAdBanner = staticCompositionLocalOf<GoogleAdBannerState?> {
    error("No MainBannerAdHandle provided")
}

object GoogleAdUnitIds {
    const val TEST_BANNER = "ca-app-pub-3940256099942544/9214589741"

    const val HOME_BANNER = "ca-app-pub-9245072226361042/4144884869"
}

@Composable
internal expect fun GoogleAdBannerView(
    modifier: Modifier = Modifier,
    bannerState: GoogleAdBannerState,
)

expect class GoogleAdBannerState

@Composable
expect fun rememberGoggleAdBannerState(
    adUnitId: String,
): GoogleAdBannerState

