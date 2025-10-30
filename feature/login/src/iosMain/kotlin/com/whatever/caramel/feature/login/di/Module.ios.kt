package com.whatever.caramel.feature.login.di

import com.whatever.caramel.feature.login.social.apple.AppleAuthProvider
import com.whatever.caramel.feature.login.social.apple.AppleAuthProviderImpl
import com.whatever.caramel.feature.login.social.kakao.KakaoAuthProvider
import com.whatever.caramel.feature.login.social.kakao.KakaoAuthProviderImpl
import kakaoLoginBridge.KakaoLoginBridge
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module

@OptIn(ExperimentalForeignApi::class)
actual val socialModule: Module
    get() =
        module {
            factory<KakaoAuthProvider> { KakaoAuthProviderImpl(bridge = KakaoLoginBridge()) }
            factory<AppleAuthProvider> { AppleAuthProviderImpl() }
        }
