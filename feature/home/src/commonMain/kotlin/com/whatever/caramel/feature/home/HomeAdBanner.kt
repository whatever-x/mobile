package com.whatever.caramel.feature.home

import androidx.compose.runtime.staticCompositionLocalOf
import com.whatever.caramel.core.ui.admob.GoogleAdBannerState

val LocalHomeGoogleAdBanner =
    staticCompositionLocalOf<GoogleAdBannerState> {
        error("No HomeGoogleAdBanner provided")
    }
