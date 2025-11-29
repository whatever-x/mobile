package com.whatever.caramel.core.ui.admob

import GoogleMobileAds.GADBannerView
import GoogleMobileAds.GADBannerViewDelegateProtocol
import io.github.aakira.napier.Napier
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSError
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
class BannerViewDelegate(

) : NSObject(), GADBannerViewDelegateProtocol {

    override fun bannerViewDidReceiveAd(bannerView: GADBannerView) {
        Napier.d { "iOS 배너 로그 성공: $bannerView" }
    }

    override fun bannerView(
        bannerView: GADBannerView,
        didFailToReceiveAdWithError: NSError
    ) {
        // TODO : 배너 배치 실패 시 Crash 로그 수집
        Napier.e { "iOS 배너 실패 : $didFailToReceiveAdWithError" }
    }

}
