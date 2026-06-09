@file:JvmName("AndroidAppModuleKt")

package com.whatever.caramel.di

import android.app.Activity
import com.whatever.caramel.MainActivity
import com.whatever.caramel.core.firebaseMessaging.NotificationIntentProvider
import com.whatever.caramel.core.inAppReview.CaramelInAppReview
import com.whatever.caramel.core.inAppReview.CaramelInAppReviewImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val androidAppModule: Module
    get() =
        module {
            single<NotificationIntentProvider> { AppNotificationIntentProvider() }
            scope<MainActivity> {
                scoped<CaramelInAppReview> { (activity: Activity) ->
                    CaramelInAppReviewImpl(activityProvider = { activity })
                }
            }
        }
