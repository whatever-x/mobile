package com.whatever.caramel.external.admob.banner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode

val LocalHomeGoogleAdBanner =
    staticCompositionLocalOf<GoogleAdBannerState> {
        error("No HomeGoogleAdBanner provided")
    }

@Composable
fun CaramelGoogleAdBanner(
    modifier: Modifier = Modifier,
    bannerState: GoogleAdBannerState,
) {
    val isPreviewMode = LocalInspectionMode.current

    if (isPreviewMode) {
        Box(
            modifier = modifier.background(color = Color.White),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "배너 광고 자리",
            )
        }
    } else {
        GoogleAdBannerView(
            modifier = modifier,
            bannerState = bannerState,
        )
    }
}
