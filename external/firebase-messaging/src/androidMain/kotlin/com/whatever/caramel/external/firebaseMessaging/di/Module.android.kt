package com.whatever.caramel.external.firebaseMessaging.di

import com.whatever.caramel.external.firebaseMessaging.FcmTokenProvider
import com.whatever.caramel.external.firebaseMessaging.FcmTokenProviderImpl
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
