@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.whatever.caramel.core.ui.admob

import android.Manifest
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.whatever.caramel.core.ui.BuildConfig
import com.whatever.caramel.core.ui.admob.GoogleAdBannerType.AnchoredAdaptive.Companion.SCREEN_FULL_WIDTH

actual object GoogleAdUnitIds {
    actual val TEST_BANNER = BuildConfig.ADMOB_TEST_BANNER_ID

    actual val HOME_BANNER = BuildConfig.ADMOB_HOME_BANNER_ID
}

@Composable
internal actual fun GoogleAdBannerView(
    modifier: Modifier,
    bannerState: GoogleAdBannerState,
) {
    AndroidView(
        modifier = modifier,
        factory = {
            bannerState.adView.detachFromParent()
            bannerState.adView
        },
        onReset = {
            // LazyColumn 내부에서 AdView를 재사용해 스크롤 시 광고 재로딩을 줄이기 위한 빈 람다 생성
        },
        onRelease = { adView -> adView.detachFromParent() },
    )
}

@Stable
actual class GoogleAdBannerState(
    internal val adView: AdView,
)

@RequiresPermission(Manifest.permission.INTERNET)
@Composable
actual fun rememberGoogleAdBannerState(
    adUnitId: String,
    bannerType: GoogleAdBannerType,
): GoogleAdBannerState {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val adView =
        remember(adUnitId, bannerType) {
            createAdView(
                context = context,
                adUnitId = adUnitId,
                bannerType = bannerType,
            )
        }

    DisposableEffect(adView, lifecycleOwner) {
        val observer =
            LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_RESUME -> adView.resume()
                    Lifecycle.Event.ON_PAUSE -> adView.pause()
                    else -> Unit
                }
            }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    DisposableEffect(adView) {
        onDispose {
            adView.detachFromParent()
            adView.destroy()
        }
    }

    return GoogleAdBannerState(adView = adView)
}

@RequiresPermission(Manifest.permission.INTERNET)
private fun createAdView(
    context: Context,
    adUnitId: String,
    bannerType: GoogleAdBannerType,
): AdView {
    val adSize =
        when (bannerType) {
            is GoogleAdBannerType.InlineAdaptive -> {
                AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(context, bannerType.width)
            }

            is GoogleAdBannerType.AnchoredAdaptive -> {
                if (bannerType.width != SCREEN_FULL_WIDTH) {
                    AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, bannerType.width)
                } else {
                    AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, AdSize.FULL_WIDTH)
                }
            }
        }

    return AdView(context).apply {
        adListener =
            object : AdListener() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    // TODO : 배너 배치 실패 시 Crash 로그 수집
                }
            }
        this.adUnitId = adUnitId
        setAdSize(adSize)
        loadAd(AdRequest.Builder().build())
    }
}

private fun View.detachFromParent() {
    (parent as? ViewGroup)?.removeView(this)
}
