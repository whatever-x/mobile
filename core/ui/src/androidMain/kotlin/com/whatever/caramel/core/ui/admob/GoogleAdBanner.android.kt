@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.whatever.caramel.core.ui.admob

import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
internal actual fun GoogleAdBannerView(
    modifier: Modifier,
    bannerState: GoogleAdBannerState,
) {
    AndroidView(
        modifier = modifier,
        factory = { bannerState.adView },
    )
}

@Stable
actual class GoogleAdBannerState(
    internal val adView: AdView
)

@RequiresPermission(android.Manifest.permission.INTERNET)
@Composable
actual fun rememberGoggleAdBannerState(
    adUnitId: String,
): GoogleAdBannerState {
    val context = LocalContext.current

    val adView = remember(adUnitId, context) {
        AdView(context).apply {
            this.adUnitId = adUnitId
            setAdSize(AdSize.BANNER)
            loadAd(AdRequest.Builder().build())
        }
    }

    LifecycleResumeEffect(adView) {
        adView.resume()

        onPauseOrDispose {
            adView.pause()
        }
    }

    DisposableEffect(adView) {
        onDispose {
            adView.destroy()
        }
    }

    return GoogleAdBannerState(adView)
}
