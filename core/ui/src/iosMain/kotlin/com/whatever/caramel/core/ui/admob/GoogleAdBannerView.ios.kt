@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@file:OptIn(ExperimentalForeignApi::class)

package com.whatever.caramel.core.ui.admob

import GoogleMobileAds.GADBannerView
import GoogleMobileAds.GADCurrentOrientationAnchoredAdaptiveBannerAdSizeWithWidth
import GoogleMobileAds.GADCurrentOrientationInlineAdaptiveBannerAdSizeWithWidth
import GoogleMobileAds.GADRequest
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.uikit.LocalUIViewController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitView
import com.whatever.caramel.core.ui.admob.GoogleAdBannerType.AnchoredAdaptive.Companion.SCREEN_FULL_WIDTH
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.Foundation.NSBundle
import platform.UIKit.UIScreen

actual object GoogleAdUnitIds {
    actual val TEST_BANNER = NSBundle.mainBundle.objectForInfoDictionaryKey("GoogleAdmobTestBannerId") as String

    actual val HOME_BANNER = NSBundle.mainBundle.objectForInfoDictionaryKey("GoogleAdmobHomeBannerId") as String
}

/**
 * Android와 다르게 배너뷰의 높이를 자동으로 측정해주지 않아 공식 문서에 나온 배너뷰 높이의 최소/최대 값 적용
 */
@Composable
internal actual fun GoogleAdBannerView(
    modifier: Modifier,
    bannerState: GoogleAdBannerState,
) {
    UIKitView(
        modifier =
            modifier.heightIn(
                min = 50.dp,
                max = 250.dp,
            ),
        factory = { bannerState.bannerView },
    )
}

@Stable
actual class GoogleAdBannerState(
    internal val bannerView: GADBannerView,
)

@Composable
actual fun rememberGoogleAdBannerState(
    adUnitId: String,
    bannerType: GoogleAdBannerType,
): GoogleAdBannerState {
    val rootViewController = LocalUIViewController.current
    val delegate = remember { GoogleAdBannerViewDelegate() }
    val adSize =
        when (bannerType) {
            is GoogleAdBannerType.InlineAdaptive -> {
                GADCurrentOrientationInlineAdaptiveBannerAdSizeWithWidth(width = bannerType.width.toDouble())
            }

            is GoogleAdBannerType.AnchoredAdaptive -> {
                if (bannerType.width != SCREEN_FULL_WIDTH) {
                    GADCurrentOrientationAnchoredAdaptiveBannerAdSizeWithWidth(width = bannerType.width.toDouble())
                } else {
                    val screenWidth = UIScreen.mainScreen.bounds.useContents { size.width }
                    GADCurrentOrientationAnchoredAdaptiveBannerAdSizeWithWidth(width = screenWidth)
                }
            }
        }

    val bannerView =
        remember(adUnitId, bannerType, rootViewController) {
            GADBannerView().apply {
                this.adSize = adSize
                this.adUnitID = adUnitId
                this.rootViewController = rootViewController
                this.delegate = delegate

                val request = GADRequest()
                this.loadRequest(request)
            }
        }

    DisposableEffect(bannerView) {
        onDispose {
            bannerView.delegate = null
            bannerView.rootViewController = null
        }
    }

    return GoogleAdBannerState(bannerView = bannerView)
}
