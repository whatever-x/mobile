import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("caramel.android.application")
    id("caramel.kmp")
    id("caramel.kmp.ios")
    id("caramel.compose")
    id("caramel.kotlin.serialization")
    id("caramel.google.services")
}

android.namespace = "com.whatever.caramel.app"

kotlin {

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            implementation(libs.ktor.client.okhttp)
            implementation(project.dependencies.platform(libs.firebase.bom.android))
            implementation(libs.firebase.analytics.ktx)
            implementation(libs.firebase.crashlytics.ktx)
            implementation(libs.apps.flyer)
        }
        commonMain.dependencies {
            // Project
            implementation(projects.core.designsystem)
            implementation(projects.core.ui)
            implementation(projects.core.domain)
            implementation(projects.core.data)
            implementation(projects.core.datastore)
            implementation(projects.core.database)
            implementation(projects.core.remote)
            api(projects.core.analytics)
            implementation(projects.core.viewmodel)
            api(projects.core.deeplink)
            api(projects.core.firebaseMessaging)
            implementation(projects.feature.profile.edit)
            implementation(projects.feature.profile.create)
            implementation(projects.feature.splash)
            implementation(projects.feature.setting)
            implementation(projects.feature.login)
            implementation(projects.feature.calendar)
            implementation(projects.feature.couple.connect)
            implementation(projects.feature.couple.invite)
            implementation(projects.feature.couple.connecting)
            implementation(projects.feature.home)
            implementation(projects.feature.login)
            implementation(projects.feature.main)
            implementation(projects.feature.memo)
            implementation(projects.feature.content.create)
            implementation(projects.feature.content.edit)
            implementation(projects.feature.content.detail)

            // Library
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.jetbrains.compose.navigation)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.androidx.room.runtime)
            implementation(libs.sqlite.bundled)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.core)

            implementation(libs.bundles.ktor)
            implementation(libs.bundles.coil)
            implementation(libs.bundles.moko)
            implementation(libs.bundles.datastore)
        }
        nativeMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    defaultConfig {
        val properties = Properties().apply { load(rootProject.file("local.properties").inputStream()) }
        val appsFlyerKey = "APPS_FLYER_KEY"

        buildConfigField(
            type = "String",
            name = appsFlyerKey,
            value = properties.getProperty(appsFlyerKey)
        )
    }

    signingConfigs {
        getByName("debug") {
            Properties().run {
                load(FileInputStream(rootProject.file("local.properties")))
                storeFile = rootProject.file(this["STORE_FILE"] as String)
                keyAlias = this["KEY_ALIAS"] as String
                keyPassword = this["KEY_PASSWORD"] as String
                storePassword = this["STORE_PASSWORD"] as String
            }
        }

        create("release") {
            Properties().run {
                load(FileInputStream(rootProject.file("local.properties")))
                storeFile = rootProject.file(this["STORE_FILE"] as String)
                keyAlias = this["KEY_ALIAS"] as String
                keyPassword = this["KEY_PASSWORD"] as String
                storePassword = this["STORE_PASSWORD"] as String
            }
        }
    }
}