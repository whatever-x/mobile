package com.whatever.caramel.core.inAppReview

import android.app.Activity
import com.google.android.play.core.review.ReviewManagerFactory

class CaramelInAppReviewImpl(
    private val activityProvider: (() -> Activity)? = null,
) : CaramelInAppReview {
    override fun requestReview() {
        val activity = activityProvider?.invoke() ?: return
        val manager = ReviewManagerFactory.create(activity)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { request ->
            if (request.isSuccessful) {
                manager.launchReviewFlow(activity, request.result)
            }
        }
    }
}