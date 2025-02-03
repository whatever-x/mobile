package com.whatever.caramel.di

import com.whatever.caramel.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.dsl.module

// expect는 platform별로 패키지까지 동일하게 사용해야함
expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
}