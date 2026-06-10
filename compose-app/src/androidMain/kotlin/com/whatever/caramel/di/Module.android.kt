package com.whatever.caramel.di

import com.whatever.caramel.app.CaramelViewModel
import com.whatever.caramel.app.util.PlatformManager
import com.whatever.caramel.core.domain.vo.app.Platform
import com.whatever.caramel.core.inAppReview.CaramelInAppReview
import com.whatever.caramel.core.inAppReview.CaramelInAppReviewImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

actual val appModule: Module
    get() =
        module {
            viewModelOf(::CaramelViewModel)
            single<Platform> { PlatformManager() }
            single<CaramelInAppReview> { CaramelInAppReviewImpl() }
        }
