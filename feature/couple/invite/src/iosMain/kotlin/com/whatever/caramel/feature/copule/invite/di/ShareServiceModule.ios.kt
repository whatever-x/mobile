package com.whatever.caramel.feature.copule.invite.di

import com.whatever.caramel.feature.copule.invite.share.ShareController
import com.whatever.caramel.feature.copule.invite.share.ShareService
import org.koin.core.module.Module
import org.koin.dsl.module

actual val shareServiceModule: Module = module {
    single<ShareService> { ShareController() }
}