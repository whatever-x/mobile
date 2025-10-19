package com.whatever.caramel.core.inAppReview.di

import android.app.Activity
import com.whatever.caramel.core.inAppReview.CaramelInAppReview
import com.whatever.caramel.core.inAppReview.CaramelInAppReviewImpl
import org.koin.core.module.Module
import org.koin.dsl.module

actual val inAppReviewModule: Module = module {
    single<CaramelInAppReview> {
        CaramelInAppReviewImpl(
            activityProvider = { get<Activity>() }
        )
    }
}