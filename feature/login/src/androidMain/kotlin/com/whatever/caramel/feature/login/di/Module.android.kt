package com.whatever.caramel.feature.login.di

import com.whatever.caramel.feature.login.social.kakao.KakaoAuthProviderImpl
import com.whatever.caramel.feature.login.social.kakao.KakaoAuthProvider
import org.koin.core.module.Module
import org.koin.dsl.module

actual val socialModule: Module
    get() = module {
        factory<KakaoAuthProvider> { KakaoAuthProviderImpl() }
    }