package com.whatever.caramel.di

import com.whatever.caramel.core.data.createDataStore
import com.whatever.caramel.feat.login.social.KakaoAuthProviderImpl
import com.whatever.caramel.feat.login.social.kakao.KakaoAuthProvider
import com.whatever.caramel.feat.sample.data.database.DatabaseFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
        single { DatabaseFactory(androidApplication()) }
        single { createDataStore(androidApplication()) }
    }

actual val socialModule: Module
    get() = module {
        factory<KakaoAuthProvider> { KakaoAuthProviderImpl() }
    }