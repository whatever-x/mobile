package com.whatever.caramel.core.firebaseMessaging.di

import com.whatever.caramel.core.firebaseMessaging.FcmTokenProvider
import com.whatever.caramel.core.firebaseMessaging.FcmTokenProviderImpl
import firebaseBridge.FcmTokenBridge
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module

@OptIn(ExperimentalForeignApi::class)
actual val firebaseMessagingModule: Module =
    module {
        single<FcmTokenProvider> {
            FcmTokenProviderImpl(
                fcmTokenBridge = FcmTokenBridge(),
                remoteFirebaseControllerDataSource = get()
            )
        }
    }