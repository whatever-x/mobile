package com.whatever.caramel.external.deeplink.di

import com.whatever.caramel.external.deeplink.DeepLinkHandler
import com.whatever.caramel.external.deeplink.DeepLinkHandlerImpl
import org.koin.dsl.module

val deepLinkModule =
    module {
        single<DeepLinkHandler> { DeepLinkHandlerImpl() }
    }
