package com.whatever.caramel.core.inAppReview

import platform.StoreKit.SKStoreReviewController
import platform.UIKit.UIApplication
import platform.UIKit.UIWindowScene

class CaramelInAppReviewImpl : CaramelInAppReview {
    override fun requestReview() {
        val windowScene = UIApplication.sharedApplication.connectedScenes.first() as? UIWindowScene
        if (windowScene != null) {
            SKStoreReviewController.requestReviewInScene(windowScene)
        }
    }
}
