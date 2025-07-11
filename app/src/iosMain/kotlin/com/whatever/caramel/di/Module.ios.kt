package com.whatever.caramel.di

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.app.CaramelViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

actual val appViewModelModule: Module
    get() =
        module {
            viewModel {
                CaramelViewModel(
                    connectCoupleUseCase = get(),
                    deepLinkHandler = get(),
                    savedStateHandle = SavedStateHandle(),
                    crashlytics = get()
                /*
                 @ham2174 FIXME : SavedStateHandle Ios에서 사용 불가능.
                 koin-compose-viewmodel 버전이 업데이트 되기 전까지 CaramelViewModel 의 SavedStateHandle 사용 불가
                 */
                )
            }
        }
