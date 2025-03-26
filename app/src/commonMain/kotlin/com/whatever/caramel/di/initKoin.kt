package com.whatever.caramel.di

import com.whatever.caramel.core.data.di.networkInterceptorModule
import com.whatever.caramel.core.data.di.repositoryModule
import com.whatever.caramel.core.database.di.databaseModule
import com.whatever.caramel.core.database.di.platformDatabaseModule
import com.whatever.caramel.core.datastore.di.dataStoreModule
import com.whatever.caramel.core.datastore.di.platformDataStoreModule
import com.whatever.caramel.core.remote.di.networkClientEngineModule
import com.whatever.caramel.core.remote.di.networkModule
import com.whatever.caramel.core.remote.di.remoteDataSourceModule
import com.whatever.caramel.feature.calendar.di.calendarFeatureModule
import com.whatever.caramel.feature.content.di.contentFeatureModule
import com.whatever.caramel.feature.copule.invite.di.coupleInviteFeatureModule
import com.whatever.caramel.feature.copule.invite.di.shareServiceModule
import com.whatever.caramel.feature.couple.connect.coupleConnectFeatureModule
import com.whatever.caramel.feature.home.di.homeFeatureModule
import com.whatever.caramel.feature.login.di.loginFeatureModule
import com.whatever.caramel.feature.login.di.socialModule
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
            /* ==== Data Layer ==== */
            repositoryModule,

            /* Remote Module */
            networkModule,
            networkClientEngineModule,
            networkInterceptorModule,
            remoteDataSourceModule,

            /* DataBase Module */
            platformDatabaseModule,
            databaseModule,

            /* DataStore Module */
            platformDataStoreModule,
            dataStoreModule,

            /* ==== Presentation Layer ==== */
            calendarFeatureModule,
            contentFeatureModule,
            coupleConnectFeatureModule,
            coupleInviteFeatureModule,
            shareServiceModule,
            homeFeatureModule,
            loginFeatureModule,
            socialModule,
            memoFeatureModule,
            onboardingFeatureModule,
            profileCreateFeatureModule,
            profileEditFeatureModule,
            settingFeatureModule,
            splashFeatureModule,
        )
    }
}