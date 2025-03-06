package com.whatever.caramel.di

import com.whatever.caramel.core.database.di.databaseModule
import com.whatever.caramel.core.database.di.platformDatabaseModule
import com.whatever.caramel.core.datastore.di.localDataSourceModule
import com.whatever.caramel.core.datastore.di.platformDataStoreModule
import com.whatever.caramel.core.data.di.networkModule
import com.whatever.caramel.core.data.di.repositoryModule
import com.whatever.caramel.core.remote.di.networkClientModule
import com.whatever.caramel.core.remote.di.remoteModule
import com.whatever.caramel.feature.calendar.di.calendarFeatureModule
import com.whatever.caramel.feature.content.di.contentFeatureModule
import com.whatever.caramel.feature.copule.invite.coupleInviteFeatureModule
import com.whatever.caramel.feature.couple.connect.coupleConnectFeatureModule
import com.whatever.caramel.feature.home.di.homeFeatureModule
import com.whatever.caramel.feature.login.di.loginFeatureModule
import com.whatever.caramel.feature.memo.di.memoFeatureModule
import com.whatever.caramel.feature.onboarding.di.onboardingFeatureModule
import com.whatever.caramel.feature.profile.create.di.profileCreateFeatureModule
import com.whatever.caramel.feature.profile.edit.di.profileEditFeatureModule
import com.whatever.caramel.feature.setting.di.settingFeatureModule
import com.whatever.caramel.feature.splash.di.splashFeatureModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            databaseModule,
            networkModule,
            platformDatabaseModule,
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