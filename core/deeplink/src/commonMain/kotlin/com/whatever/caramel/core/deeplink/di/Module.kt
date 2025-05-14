package com.whatever.caramel.core.deeplink.di

import com.whatever.caramel.core.deeplink.DeepLinkHandler
import com.whatever.caramel.core.deeplink.DeepLinkHandlerImpl
import org.koin.dsl.module

val deepLinkModule = module {
    single<DeepLinkHandler> { DeepLinkHandlerImpl() }
}