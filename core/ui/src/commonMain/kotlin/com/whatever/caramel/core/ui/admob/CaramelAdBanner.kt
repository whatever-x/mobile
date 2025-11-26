package com.whatever.caramel.core.ui.admob

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CaramelGoogleAdBanner(
    modifier: Modifier = Modifier,
    bannerState: GoogleAdBannerState,
) {
    GoogleAdBannerView(
        modifier = modifier,
        bannerState = bannerState,
    )
}
