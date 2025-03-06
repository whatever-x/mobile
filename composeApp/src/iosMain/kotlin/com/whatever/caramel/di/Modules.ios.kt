package com.whatever.caramel.di

import com.whatever.caramel.core.data.createDataStore
import com.whatever.caramel.feat.login.social.KakaoAuthProviderImpl
import com.whatever.caramel.feat.login.social.kakao.KakaoAuthProvider
import com.whatever.caramel.feat.sample.data.database.DatabaseFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import swiftBridge.KakaoLoginBridge

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { Darwin.create() }
        single { DatabaseFactory() }
        single { createDataStore() }
    }

@OptIn(ExperimentalForeignApi::class)
actual val socialModule: Module
    get() = module {
        factory<KakaoAuthProvider> {
            KakaoAuthProviderImpl(
                bridge = KakaoLoginBridge()
            )
        }
    }