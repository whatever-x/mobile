package com.whatever.caramel.feature.login.di

import com.whatever.caramel.feature.login.social.KakaoAuthProviderImpl
import com.whatever.caramel.feature.login.social.apple.AppleAuthProvider
import com.whatever.caramel.feature.login.social.apple.AppleAuthProviderImpl
import com.whatever.caramel.feature.login.social.kakao.KakaoAuthProvider
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import swiftBridge.KakaoLoginBridge

@OptIn(ExperimentalForeignApi::class)
actual val socialModule: Module
    get() = module {
        factory<KakaoAuthProvider> {
            KakaoAuthProviderImpl(
                bridge = KakaoLoginBridge()
            )
        }
        factory<AppleAuthProvider> { AppleAuthProviderImpl() }
    }