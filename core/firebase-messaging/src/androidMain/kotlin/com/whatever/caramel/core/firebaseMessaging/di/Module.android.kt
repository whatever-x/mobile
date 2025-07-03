package com.whatever.caramel.core.firebaseMessaging.di

import com.whatever.caramel.core.firebaseMessaging.FcmTokenProvider
import com.whatever.caramel.core.firebaseMessaging.FcmTokenProviderImpl
import org.koin.core.module.Module
import org.koin.dsl.module

actual val firebaseMessagingModule: Module =
    module {
        single<FcmTokenProvider> {
            FcmTokenProviderImpl(
                remoteFirebaseControllerDataSource = get(),
            )
        }
    }
