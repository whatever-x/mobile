package com.whatever.caramel.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.whatever.caramel.core.data.HttpClientFactory
import com.whatever.caramel.core.data.NetworkConfig
import com.whatever.caramel.feat.sample.data.database.DatabaseFactory
import com.whatever.caramel.feat.sample.data.database.SampleDatabase
import com.whatever.caramel.feat.sample.data.local.LocalSampleDataSource
import com.whatever.caramel.feat.sample.data.local.LocalSampleDataSourceImpl
import com.whatever.caramel.feat.content.ContentViewModel
import com.whatever.caramel.feat.couple.code.CoupleConnectViewModel
import com.whatever.caramel.feat.couple.invite.CoupleInviteViewModel
import com.whatever.caramel.feat.login.LoginViewModel
import com.whatever.caramel.feat.login.social.kakao.KakaoAuthProvider
import com.whatever.caramel.feat.main.calendar.CalendarViewModel
import com.whatever.caramel.feat.main.home.HomeViewModel
import com.whatever.caramel.feat.main.memo.MemoViewModel
import com.whatever.caramel.feat.onboarding.OnboardingViewModel
import com.whatever.caramel.feat.profile.create.ProfileCreateViewModel
import com.whatever.caramel.feat.profile.edit.ProfileEditViewModel
import com.whatever.caramel.feat.sample.data.remote.RemoteSampleDataSource
import com.whatever.caramel.feat.sample.data.remote.RemoteSampleDataSourceImpl
import com.whatever.caramel.feat.sample.data.repository.SampleRepositoryImpl
import com.whatever.caramel.feat.sample.domain.SampleRepository
import com.whatever.caramel.feat.sample.presentation.SampleViewModel
import com.whatever.caramel.feat.setting.SettingViewModel
import com.whatever.caramel.feat.splash.SplashViewModel
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
    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<SampleDatabase>().sampleDao }
    single<RemoteSampleDataSource> { RemoteSampleDataSourceImpl(client = get()) }
    single<SampleRepository> { SampleRepositoryImpl(remoteSampleDataSource = get(), localSampleDataSource = get()) }
    single<LocalSampleDataSource> { LocalSampleDataSourceImpl(prefs = get(), sampleDao = get()) }

    viewModelOf(::SampleViewModel)
}

val splashFeatureModule = module {
    viewModelOf(::SplashViewModel)
}

val onboardingFeatureModule = module {
    viewModelOf(::OnboardingViewModel)
}

expect val socialModule: Module

val loginFeatureModule = module {
    viewModelOf(::LoginViewModel)
}

val profileFeatureModule = module {
    viewModelOf(::ProfileCreateViewModel)
    viewModelOf(::ProfileEditViewModel)
}

val coupleFeatureModule = module {
    viewModelOf(::CoupleConnectViewModel)
    viewModelOf(::CoupleInviteViewModel)
}

val mainFeatureModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::CalendarViewModel)
    viewModelOf(::MemoViewModel)
}

val settingFeatureModule = module {
    viewModelOf(::SettingViewModel)
}

val contentFeatureModule = module {
    viewModelOf(::ContentViewModel)
}
