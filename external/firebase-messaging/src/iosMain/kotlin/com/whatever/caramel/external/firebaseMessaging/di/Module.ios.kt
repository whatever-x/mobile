package com.whatever.caramel.external.firebaseMessaging.di

import com.whatever.caramel.external.firebaseMessaging.FcmTokenProvider
import com.whatever.caramel.external.firebaseMessaging.FcmTokenProviderImpl
import firebaseMessagingBridge.FcmTokenBridge
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module

@OptIn(ExperimentalForeignApi::class)
actual val firebaseMessagingModule: Module =
    module {
        single<FcmTokenProvider> {
            FcmTokenProviderImpl(
                fcmTokenBridge = FcmTokenBridge(),
                remoteFirebaseControllerDataSource = get(),
            )
        }
    }
