@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.whatever.caramel.external.admob.banner

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.whatever.caramel.external.admob.banner.GoogleAdBannerType.AnchoredAdaptive.Companion.SCREEN_FULL_WIDTH

actual object GoogleAdUnitIds {
    actual const val TEST_BANNER = "ca-app-pub-3940256099942544/9214589741"

    actual const val HOME_BANNER = "ca-app-pub-9245072226361042/4144884869"
}

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
    internal val adView: AdView,
)

@Composable
actual fun rememberGoogleAdBannerState(
    adUnitId: String,
    bannerType: GoogleAdBannerType,
): GoogleAdBannerState {
    val context = LocalContext.current
    val bannerSize =
        when (bannerType) {
            is GoogleAdBannerType.InlineAdaptive -> {
                AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(context, bannerType.width)
            }

            is GoogleAdBannerType.AnchoredAdaptive -> {
                if (bannerType.width != SCREEN_FULL_WIDTH) {
                    AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                        context,
                        bannerType.width,
                    )
                } else {
                    AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                        context,
                        AdSize.FULL_WIDTH,
                    )
                }
            }
        }

    val adView =
        remember {
            AdView(context).apply {
                this.adListener =
                    object : AdListener() {
                        override fun onAdFailedToLoad(p0: LoadAdError) {
                            super.onAdFailedToLoad(p0)
                            // TODO : 배너 배치 실패 시 Crash 로그 수집
                        }
                    }
                this.adUnitId = adUnitId
                setAdSize(bannerSize)
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

    return GoogleAdBannerState(
        adView = adView,
    )
}
