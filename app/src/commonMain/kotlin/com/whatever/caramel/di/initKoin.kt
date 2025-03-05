package com.whatever.caramel.di

import com.whatever.caramel.core.data.database.di.databaseModule
import com.whatever.caramel.core.data.datastore.di.localDataSourceModule
import com.whatever.caramel.core.data.datastore.di.platformDataStoreModule
import com.whatever.caramel.core.data.di.networkModule
import com.whatever.caramel.core.data.di.repositoryModule
import com.whatever.caramel.core.data.remote.di.networkClientModule
import com.whatever.caramel.core.data.remote.di.remoteModule
import com.whatever.caramel.feature.calendar.di.calendarFeatureModule
import com.whatever.caramel.feature.content.contentFeatureModule
import com.whatever.caramel.feature.copule.invite.coupleInviteFeatureModule
import com.whatever.caramel.feature.couple.connect.coupleConnectFeatureModule
import com.whatever.caramel.feature.home.homeFeatureModule
import com.whatever.caramel.feature.login.loginFeatureModule
import com.whatever.caramel.feature.memo.memoFeatureModule
import com.whatever.caramel.feature.onboarding.onboardingFeatureModule
import com.whatever.caramel.feature.profile.create.profileCreateFeatureModule
import com.whatever.caramel.feature.profile.edit.profileEditFeatureModule
import com.whatever.caramel.feature.setting.settingFeatureModule
import com.whatever.caramel.feature.splash.splashFeatureModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            databaseModule,
            networkModule,
            platformDataStoreModule,
            localDataSourceModule,
            networkClientModule,
            remoteModule,
            repositoryModule,
            calendarFeatureModule,
            contentFeatureModule,
            coupleConnectFeatureModule,
            coupleInviteFeatureModule,
            homeFeatureModule,
            loginFeatureModule,
            memoFeatureModule,
            onboardingFeatureModule,
            profileCreateFeatureModule,
            profileEditFeatureModule,
            settingFeatureModule,
            splashFeatureModule,
        )
    }
}