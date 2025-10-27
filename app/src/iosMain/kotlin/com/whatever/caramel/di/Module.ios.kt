package com.whatever.caramel.di

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.app.CaramelViewModel
import com.whatever.caramel.app.util.PlatformManager
import com.whatever.caramel.core.domain.vo.app.Platform
import com.whatever.caramel.core.inAppReview.CaramelInAppReview
import com.whatever.caramel.core.inAppReview.CaramelInAppReviewImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

actual val appModule: Module
    get() =
        module {
            viewModel {
                CaramelViewModel(
                    connectCoupleUseCase = get(),
                    deepLinkHandler = get(),
                    savedStateHandle = SavedStateHandle(),
                    crashlytics = get(),
                    analytics = get(),
                    checkInAppReviewAvailableUseCase = get()
                /*
                 @ham2174 FIXME : SavedStateHandle Ios에서 사용 불가능.
                 koin-compose-viewmodel 버전이 업데이트 되기 전까지 CaramelViewModel 의 SavedStateHandle 사용 불가
                 */
                )
            }
            single<Platform> { PlatformManager() }
            single<CaramelInAppReview> { CaramelInAppReviewImpl() }
        }
