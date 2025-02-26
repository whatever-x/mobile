package com.whatever.caramel.di

import com.whatever.caramel.core.data.HttpClientFactory
import com.whatever.caramel.core.data.NetworkConfig
import com.whatever.caramel.feat.couple.presentation.code.CoupleCodeViewModel
import com.whatever.caramel.feat.couple.presentation.invite.CoupleInviteViewModel
import com.whatever.caramel.feat.login.presentation.LoginViewModel
import com.whatever.caramel.feat.main.presentation.calendar.CalendarViewModel
import com.whatever.caramel.feat.main.presentation.home.HomeViewModel
import com.whatever.caramel.feat.onboarding.presentation.OnboardingViewModel
import com.whatever.caramel.feat.profile.presentation.create.ProfileCreateViewModel
import com.whatever.caramel.feat.sample.data.remote.RemoteSampleDataSource
import com.whatever.caramel.feat.sample.data.remote.RemoteSampleDataSourceImpl
import com.whatever.caramel.feat.sample.data.repository.SampleRepositoryImpl
import com.whatever.caramel.feat.sample.domain.SampleRepository
import com.whatever.caramel.feat.sample.presentation.SampleViewModel
import com.whatever.caramel.feat.splash.presentation.SplashViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

expect val platformModule: Module

val networkModule = module {
    single {
        HttpClientFactory.create(
            isDebug = NetworkConfig.isDebug,
            engine = get(),
            baseUrl = NetworkConfig.BASE_URL
        )
    }
}

val sampleFeatureModule = module {
    single<RemoteSampleDataSource> { RemoteSampleDataSourceImpl(client = get()) }
    single<SampleRepository> { SampleRepositoryImpl(remoteSampleDataSource = get()) }

    viewModelOf(::SampleViewModel)
}

val splashFeatureModule = module {
    viewModelOf(::SplashViewModel)
}

val onboardingFeatureModule = module {
    viewModelOf(::OnboardingViewModel)
}

val loginFeatureModule = module {
    viewModelOf(::LoginViewModel)
}

val profileFeatureModule = module {
    viewModelOf(::ProfileCreateViewModel)
}

val coupleFeatureModule = module {
    viewModelOf(::CoupleCodeViewModel)
    viewModelOf(::CoupleInviteViewModel)
}

val mainFeatureModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::CalendarViewModel)
}