package com.whatever.caramel.core.domain.di

import com.whatever.caramel.core.domain.usecase.auth.RefreshUserSessionUseCase
import com.whatever.caramel.core.domain.usecase.auth.SignInWithSocialPlatformUseCase
import com.whatever.caramel.core.domain.usecase.user.CheckUserStateUseCase
import com.whatever.caramel.core.domain.usecase.user.CreateUserProfileUseCase
import com.whatever.caramel.core.domain.usecase.user.GetOnboardingCompletionUseCase
import com.whatever.caramel.core.domain.usecase.user.SetOnboardingCompletionUseCase
import org.koin.dsl.module

val useCaseModule = module {
    // Auth
    factory { SignInWithSocialPlatformUseCase(get(), get()) }
    factory { RefreshUserSessionUseCase(get(), get()) }

    // User
    factory { GetOnboardingCompletionUseCase(get()) }
    factory { SetOnboardingCompletionUseCase(get()) }
    factory { CheckUserStateUseCase(get()) }
    factory { CreateUserProfileUseCase(get()) }
}