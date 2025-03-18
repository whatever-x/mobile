package com.whatever.caramel.core.remote.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val networkClientEngineModule: Module
    get() = module {
        single<HttpClientEngine> { Darwin.create() }
    }