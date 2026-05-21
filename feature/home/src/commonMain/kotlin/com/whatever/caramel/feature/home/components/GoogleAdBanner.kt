package com.whatever.caramel.feature.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import com.whatever.caramel.core.ui.admob.CaramelGoogleAdBanner
import com.whatever.caramel.core.ui.admob.GoogleAdBannerState

internal fun LazyListScope.googleAdBanner(
    bannerState: GoogleAdBannerState,
) {
    item(
        key = "home_google_ad_banner",
        contentType = "google_ad_banner",
    ) {
        CaramelGoogleAdBanner(
            modifier = Modifier.fillMaxWidth(),
            bannerState = bannerState,
        )
    }
}
