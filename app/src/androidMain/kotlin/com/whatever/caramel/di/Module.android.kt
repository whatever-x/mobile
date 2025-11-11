package com.whatever.caramel.di

import android.app.Activity
import com.whatever.caramel.MainActivity
import com.whatever.caramel.app.CaramelViewModel
import com.whatever.caramel.app.util.PlatformManager
import com.whatever.caramel.core.domain.vo.app.Platform
import com.whatever.caramel.core.firebaseMessaging.NotificationIntentProvider
import com.whatever.caramel.core.inAppReview.CaramelInAppReview
import com.whatever.caramel.core.inAppReview.CaramelInAppReviewImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

actual val appModule: Module
    get() =
        module {
            viewModelOf(::CaramelViewModel)
            single<NotificationIntentProvider> { AppNotificationIntentProvider() }
            single<Platform> { PlatformManager() }
            scope<MainActivity> {
                scoped<CaramelInAppReview> { (activity: Activity) ->
                    CaramelInAppReviewImpl(activityProvider = { activity })
                }
            }
        }
