package com.whatever.caramel.core.domain.di

import com.whatever.caramel.core.domain.usecase.auth.LogoutUseCase
import com.whatever.caramel.core.domain.usecase.auth.SignInWithSocialPlatformUseCase
import com.whatever.caramel.core.domain.usecase.couple.ConnectCoupleUseCase
import com.whatever.caramel.core.domain.usecase.couple.EditCoupleStartDateUseCase
import com.whatever.caramel.core.domain.usecase.couple.GetCoupleInfoUseCase
import com.whatever.caramel.core.domain.usecase.couple.GetCoupleInvitationCodeUseCase
import com.whatever.caramel.core.domain.usecase.couple.UpdateShareMessageUseCase
import com.whatever.caramel.core.domain.usecase.user.CreateUserProfileUseCase
import com.whatever.caramel.core.domain.usecase.user.EditProfileUseCase
import com.whatever.caramel.core.domain.usecase.user.RefreshUserSessionUseCase
import org.koin.dsl.module

val useCaseModule = module {
    // Auth
    factory { SignInWithSocialPlatformUseCase(get(), get(), get()) }
    factory { RefreshUserSessionUseCase(get(), get()) }
    factory { LogoutUseCase(get()) }

    // User
    factory { CreateUserProfileUseCase(get()) }
    factory { EditProfileUseCase(get()) }

    // Couple
    factory { GetCoupleInvitationCodeUseCase(get()) }
    factory { ConnectCoupleUseCase(get(), get()) }
    factory { GetCoupleInfoUseCase(get()) }
    factory { EditCoupleStartDateUseCase(get()) }
    factory { UpdateShareMessageUseCase(get()) }

}