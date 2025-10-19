package com.whatever.caramel.di

import com.whatever.caramel.core.crashlytics.di.crashlyticsModule
import com.whatever.caramel.core.data.di.networkInterceptorModule
import com.whatever.caramel.core.data.di.repositoryModule
import com.whatever.caramel.core.database.di.databaseModule
import com.whatever.caramel.core.database.di.platformDatabaseModule
import com.whatever.caramel.core.datastore.di.dataStoreModule
import com.whatever.caramel.core.datastore.di.platformDataStoreModule
import com.whatever.caramel.core.deeplink.di.deepLinkModule
import com.whatever.caramel.core.designsystem.di.hapticControllerModule
import com.whatever.caramel.core.domain.di.useCaseModule
import com.whatever.caramel.core.firebaseMessaging.di.firebaseMessagingModule
import com.whatever.caramel.core.inAppReview.di.inAppReviewModule
import com.whatever.caramel.core.remote.di.deviceIdModule
import com.whatever.caramel.core.remote.di.networkClientEngineModule
import com.whatever.caramel.core.remote.di.networkModule
import com.whatever.caramel.core.remote.di.remoteDataSourceModule
import com.whatever.caramel.feature.calendar.di.calendarFeatureModule
import com.whatever.caramel.feature.content.create.di.contentCreateFeatureModule
import com.whatever.caramel.feature.content.detail.di.contentDetailFeatureModule
import com.whatever.caramel.feature.content.edit.di.contentEditFeatureModule
import com.whatever.caramel.feature.copule.connecting.di.coupleConnectingFeatureModule
import com.whatever.caramel.feature.copule.invite.di.coupleInviteFeatureModule
import com.whatever.caramel.feature.copule.invite.di.shareServiceModule
import com.whatever.caramel.feature.couple.connect.di.coupleConnectFeatureModule
import com.whatever.caramel.feature.home.di.homeFeatureModule
import com.whatever.caramel.feature.login.di.loginFeatureModule
import com.whatever.caramel.feature.login.di.socialModule
import com.whatever.caramel.feature.main.di.mainModule
import com.whatever.caramel.feature.memo.di.memoFeatureModule
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
            // === App Layer ===
            appModule,
            deepLinkModule,
            // ==== Data Layer ====
            repositoryModule,
            // Remote Module
            networkModule,
            networkClientEngineModule,
            networkInterceptorModule,
            remoteDataSourceModule,
            deviceIdModule,
            // DataBase Module
            platformDatabaseModule,
            databaseModule,
            // DataStore Module
            platformDataStoreModule,
            dataStoreModule,
            // FirebaseMessaging Module
            firebaseMessagingModule,
            // Crashlytics Module
            crashlyticsModule,
            // in-app review Module
            inAppReviewModule,
            // ==== Domain Layer ====
            useCaseModule,
            // ==== Presentation Layer ====
            // DesignSystem Module
            hapticControllerModule,
            // Feature Module
            calendarFeatureModule,
            contentCreateFeatureModule,
            contentDetailFeatureModule,
            contentEditFeatureModule,
            coupleConnectFeatureModule,
            coupleInviteFeatureModule,
            coupleConnectingFeatureModule,
            shareServiceModule,
            homeFeatureModule,
            loginFeatureModule,
            socialModule,
            memoFeatureModule,
            profileCreateFeatureModule,
            profileEditFeatureModule,
            settingFeatureModule,
            splashFeatureModule,
            mainModule,
        )
    }
}
